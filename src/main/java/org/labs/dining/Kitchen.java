package org.labs.dining;

import java.util.concurrent.Semaphore;

public class Kitchen {
    private final int numberOfSoupPortions;
    private final Semaphore waiters;

    private int numberOfSoupPortionsRemained;

    public Kitchen(int numberOfSoupPortions, Semaphore waiters) {
        this.numberOfSoupPortions = numberOfSoupPortions;
        this.waiters = waiters;

        numberOfSoupPortionsRemained = numberOfSoupPortions;
    }

    public int getNumberOfSoupPortions() {
        return numberOfSoupPortions;
    }

    public SoupPortion callWaiterAndGetSoupPortion() {
        try {
            waiters.acquire();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return null;
        }

        try {
            return makeSoupPortion();
        } finally {
            waiters.release();
        }
    }

    private synchronized SoupPortion makeSoupPortion() {
        if (numberOfSoupPortionsRemained > 0) {
            numberOfSoupPortionsRemained--;
            return new SoupPortion();
        }

        return null;
    }
}
