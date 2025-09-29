package org.labs.dining;

public class SoupPortion {
    public static final int CAPACITY = 20;

    private int size;

    public SoupPortion() {
        size = CAPACITY;
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
