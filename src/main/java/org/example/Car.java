package org.example;

public class Car implements Runnable {
    private final Gate gate;
    private final int carNumber;
    private final int arrivalTime;
    private final int parkDuration;
    private long waitingTime = 0;

    public Car(Gate gate, int carNumber, int arrivalTime, int parkDuration) {
        this.gate = gate;
        this.carNumber = carNumber;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
    }

    @Override
    public void run() {
        try {

            Thread.sleep(arrivalTime * 1000);
            System.out.println("Car " + carNumber + " from " + gate.getName() + " arrived at time " + arrivalTime);

            ParkingSystemSimulation.PrintLock.acquire();


            if (ParkingSystemSimulation.parkingSpots.tryAcquire()) {
                gate.incrementCarsServed();
                System.out.println("Car " + carNumber + " from " + gate.getName() + " parked. (Parking Status: "
                        + (ParkingSystemSimulation.currentCarsInParking.incrementAndGet()) + " spots occupied)");

                ParkingSystemSimulation.PrintLock.release();
            } else {

                System.out.println("Car " + carNumber + " from " + gate.getName() + " waiting for a spot.");
                ParkingSystemSimulation.PrintLock.release();
                long startWaitTime = System.currentTimeMillis();
                ParkingSystemSimulation.parkingSpots.acquire();

                ParkingSystemSimulation.PrintLock.acquire();
                gate.incrementCarsServed();


                waitingTime = (System.currentTimeMillis() - startWaitTime) / 1000;

                System.out.println("Car " + carNumber + " from " + gate.getName() + " parked after waiting for "
                        + waitingTime + " units of time. (Parking Status: "
                        + (ParkingSystemSimulation.currentCarsInParking.incrementAndGet()) + " spots occupied)");

                ParkingSystemSimulation.PrintLock.release();
            }

            Thread.sleep(parkDuration * 1000);

            ParkingSystemSimulation.PrintLock.acquire();
            System.out.println("Car " + carNumber + " from " + gate.getName() + " left after " + parkDuration + " units of time. (Parking Status: "
                    + (ParkingSystemSimulation.currentCarsInParking.decrementAndGet()) + " spots occupied)");
            ParkingSystemSimulation.parkingSpots.release();
            ParkingSystemSimulation.PrintLock.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
