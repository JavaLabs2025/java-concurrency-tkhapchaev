package org.labs.dining;

import java.util.concurrent.locks.ReentrantLock;

public class Spoon
{
    private final int id;
    private final int capacity;
    private final ReentrantLock reentrantLock;

    public Spoon(int id) {
        this.id = id;
        capacity = 1;
        reentrantLock = new ReentrantLock(true);
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void take() {
        reentrantLock.lock();
    }

    public void putBack() {
        reentrantLock.unlock();
    }
}
