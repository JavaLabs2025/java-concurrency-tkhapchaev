package org.labs.dining;

public class Programmer implements Runnable {
    private final int id;
    private final Spoon leftSpoon;
    private final Spoon rightSpoon;
    private final Kitchen kitchen;
    private final int portionsEatenLimit;

    private int portionsEaten;
    private int spoonsTaken;

    public Programmer(int id, Spoon leftSpoon, Spoon rightSpoon, Kitchen kitchen, int portionsEatenLimit) {
        this.id = id;
        this.leftSpoon = leftSpoon;
        this.rightSpoon = rightSpoon;
        this.kitchen = kitchen;
        this.portionsEatenLimit = portionsEatenLimit;

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
            if (portionsEatenLimit >= 0 && portionsEaten >= portionsEatenLimit) {
                break;
            }

            SoupPortion soupPortion = kitchen.callWaiterAndGetSoupPortion();

            if (soupPortion == null) {
                break;
            }

            while (soupPortion.getSize() > 0) {
                Spoon firstSpoon = leftSpoon.getId() < rightSpoon.getId() ? leftSpoon : rightSpoon;
                Spoon secondSpoon = firstSpoon == leftSpoon ? rightSpoon : leftSpoon;

                try {
                    int spoonsToTake = 2;
                    firstSpoon.take();
                    secondSpoon.take();

                    int amountToEat = Math.min(Spoon.CAPACITY * spoonsToTake, soupPortion.getSize());
                    soupPortion.eat(amountToEat);
                    spoonsTaken += spoonsToTake;
                } finally {
                    secondSpoon.putBack();
                    firstSpoon.putBack();
                }
            }

            portionsEaten++;
        }
    }
}
