package org.labs.dining;

public class SoupPortion {
    private final int capacity;

    private int size;

    public SoupPortion() {
        capacity = 20;
        size = capacity;
    }

    public int getSize() {
        return size;
    }

    public void eat(int amountToEat) {
        size -= amountToEat;
        if (size < 0) {
            size = 0;
        }
    }
}
