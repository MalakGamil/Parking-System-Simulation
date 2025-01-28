
package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class ParkingSystemSimulation {
    public static final int PARKING_SPOTS = 4;
    public static CustomSemaphore parkingSpots = new CustomSemaphore(PARKING_SPOTS);

    public static AtomicInteger currentCarsInParking = new AtomicInteger(0);
    private static final List<Thread> carThreads = new ArrayList<>();

    public static final CustomSemaphore PrintLock = new CustomSemaphore(1);

    public static void main(String[] args) {
        String inputFileName = "car_schedule.txt";

        Gate gate1 = new Gate("Gate 1");
        Gate gate2 = new Gate("Gate 2");
        Gate gate3 = new Gate("Gate 3");

        Map<String, Gate> gates = Map.of(
                "Gate 1", gate1,
                "Gate 2", gate2,
                "Gate 3", gate3
        );

        try {
            List<Car> cars = readInputFile(inputFileName, gates);

            for (Car car : cars) {
                Thread carThread = new Thread(car);
                carThreads.add(carThread);
                carThread.start();
            }

            for (Thread carThread : carThreads) {
                try {
                    carThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("\nSimulation Summary:");
            System.out.println("Total Cars Served: " + (gate1.getCarsServed() + gate2.getCarsServed() + gate3.getCarsServed()));
            System.out.println("Current Cars in Parking: " + currentCarsInParking);
            System.out.println("Details:");
            System.out.println("- " + gate1.getName() + " served " + gate1.getCarsServed() + " cars.");
            System.out.println("- " + gate2.getName() + " served " + gate2.getCarsServed() + " cars.");
            System.out.println("- " + gate3.getName() + " served " + gate3.getCarsServed() + " cars.");
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }

    private static List<Car> readInputFile(String fileName, Map<String, Gate> gates) throws IOException {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(", ");
                if (parts.length != 4) {
                    System.out.println("Error: Incorrect format at line " + lineNumber + ". Expected format: 'Gate X, Car Y, Arrive Z, Parks W'");
                    continue;
                }

                try {
                    Gate gate = gates.get(parts[0]);
                    int carNumber = Integer.parseInt(parts[1].split(" ")[1]);
                    int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                    int parkDuration = Integer.parseInt(parts[3].split(" ")[1]);
                    cars.add(new Car(gate, carNumber, arrivalTime, parkDuration));
                } catch (NumberFormatException | NullPointerException e) {
                    System.out.println("Error: Unable to parse data at line " + lineNumber + ". Ensure correct format and numeric values.");
                }
            }
        }
        return cars;
    }




}
