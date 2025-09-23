package org.labs.dining;

public class Programmer implements Runnable {
    private final int id;
    private final Spoon leftSpoon;
    private final Spoon rightSpoon;
    private final Kitchen kitchen;

    private int portionsEaten;
    private int spoonsTaken;

    public Programmer(int id, Spoon leftSpoon, Spoon rightSpoon, Kitchen kitchen) {
        this.id = id;
        this.leftSpoon = leftSpoon;
        this.rightSpoon = rightSpoon;
        this.kitchen = kitchen;

        portionsEaten = 0;
        spoonsTaken = 0;
    }

    public int getId() {
        return id;
    }

    public int getPortionsEaten() {
        return portionsEaten;
    }

    public int getSpoonsTaken() {
        return spoonsTaken;
    }

    @Override
    public void run() {
        while (true) {
            SoupPortion soupPortion = kitchen.callWaiterAndGetSoupPortion();

            if (soupPortion == null) {
                break;
            }

            while (soupPortion.getSize() > 0) {
                Spoon firstSpoon = leftSpoon.getId() < rightSpoon.getId() ? leftSpoon : rightSpoon;
                Spoon secondSpoon = firstSpoon == leftSpoon ? rightSpoon : leftSpoon;

                try {
                    firstSpoon.take();
                    secondSpoon.take();

                    int amountToEat = Math.min(firstSpoon.getCapacity() + secondSpoon.getCapacity(), soupPortion.getSize());
                    soupPortion.eat(amountToEat);
                    spoonsTaken += 2;
                } finally {
                    secondSpoon.putBack();
                    firstSpoon.putBack();
                }
            }

            portionsEaten++;
        }
    }
}
