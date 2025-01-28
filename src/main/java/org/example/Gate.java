package org.example;

public class Gate {
    private final String name;
    private int carsServed;

    public Gate(String name) {
        this.name = name;
        this.carsServed = 0;
    }

    public String getName() {
        return name;
    }

    public synchronized void incrementCarsServed() {
        carsServed++;
    }

    public int getCarsServed() {
        return carsServed;
    }
}

