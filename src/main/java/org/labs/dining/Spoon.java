package org.labs.dining;

import java.util.concurrent.locks.ReentrantLock;

public class Spoon
{
    public static final int CAPACITY = 1;

    private final int id;
    private final ReentrantLock reentrantLock;

    public Spoon(int id) {
        this.id = id;
        reentrantLock = new ReentrantLock(true);
    }

    public int getId() {
        return id;
    }

    public void take() {
        reentrantLock.lock();
    }

    public void putBack() {
        reentrantLock.unlock();
    }
}
