# Parking System Simulation

A Java-based simulation of a parking lot system that uses multiple gates to handle car arrivals and manages parking spots using custom semaphores. This project demonstrates concurrency, thread synchronization, and resource management.

---

## Features

1. **Simulated Parking Gates**:
   - Three gates (`Gate 1`, `Gate 2`, `Gate 3`) serve incoming cars.
   - Cars are distributed among gates based on the input schedule.

2. **Parking Spot Management**:
   - Limited parking spots (default: 4).
   - Spots are managed using a custom semaphore implementation to handle concurrent access.

3. **Car Scheduling**:
   - Cars arrive, wait for parking spots to be available, park for a specified duration, and leave.

4. **Concurrency**:
   - Each car operates as a separate thread.
   - Safe access to shared resources using semaphores and locks.

5. **Simulation Summary**:
   - Total cars served.
   - Current cars in parking.
   - Breakdown of cars served by each gate.

---

## How It Works

1. Cars are read from an input file (`car_schedule.txt`) with details about their gate, arrival time, and parking duration.
2. Each car thread attempts to acquire a parking spot when it arrives.
3. Cars park for the specified duration and release the spot afterward.
4. Gates track the number of cars they serve.

---

## Input Format

The input file `car_schedule.txt` should contain car scheduling data in the following format:
