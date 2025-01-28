package org.example;

public class CustomSemaphore {
    private int permits;

    public CustomSemaphore(int permits) {
        if (permits < 0) {
            throw new IllegalArgumentException("Permits cannot be negative");
        }
        this.permits = permits;
    }


    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        --permits;
    }


    public synchronized boolean tryAcquire() {
        if (permits > 0) {
            permits--;
            return true;
        }
        return false;
    }


    public synchronized void release() {
        permits++;
        notifyAll();
    }


    public synchronized int availablePermits() {
        return permits;
    }
}
