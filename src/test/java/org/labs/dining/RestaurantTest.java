package org.labs.dining;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {
    @Test
    public void startDinner_allProgrammersAteEqualNumberOfPortions_1() throws InterruptedException {
        int numberOfSoupPortions = 100_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 3;
        int numberOfSpoons = 3;
        int portionsEatenLimitDelta = 20;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        restaurant.startDinner();

        int delta = 100;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Programmer programmer : restaurant.getProgrammers()) {
            min = Math.min(min, programmer.getPortionsEaten());
            max = Math.max(max, programmer.getPortionsEaten());
        }

        assertTrue(max - min <= delta);
    }

    @Test
    public void startDinner_allProgrammersAteEqualNumberOfPortions_2() throws InterruptedException {
        int numberOfSoupPortions = 200_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 5;
        int numberOfSpoons = 5;
        int portionsEatenLimitDelta = 40;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        restaurant.startDinner();

        int delta = 200;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Programmer programmer : restaurant.getProgrammers()) {
            min = Math.min(min, programmer.getPortionsEaten());
            max = Math.max(max, programmer.getPortionsEaten());
        }

        assertTrue(max - min <= delta);
    }

    @Test
    public void startDinner_allProgrammersAteEqualNumberOfPortions_3() throws InterruptedException {
        int numberOfSoupPortions = 1_000_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 7;
        int numberOfSpoons = 7;
        int portionsEatenLimitDelta = 200;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        restaurant.startDinner();

        int delta = 1_000;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Programmer programmer : restaurant.getProgrammers()) {
            min = Math.min(min, programmer.getPortionsEaten());
            max = Math.max(max, programmer.getPortionsEaten());
        }

        assertTrue(max - min <= delta);
    }

    @Test
    public void startDinner_allProgrammersAteEqualNumberOfPortions_4() throws InterruptedException {
        int numberOfSoupPortions = 2_000_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 9;
        int numberOfSpoons = 9;
        int portionsEatenLimitDelta = 2000;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        restaurant.startDinner();

        int delta = 5_000;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (Programmer programmer : restaurant.getProgrammers()) {
            min = Math.min(min, programmer.getPortionsEaten());
            max = Math.max(max, programmer.getPortionsEaten());
        }

        assertTrue(max - min <= delta);
    }

    @Test
    public void startDinner_programmersAteAllPortionsAvailableInKitchen() throws InterruptedException {
        int numberOfSoupPortions = 1_000_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 7;
        int numberOfSpoons = 7;
        int portionsEatenLimitDelta = 200;
        int totalPortionsEaten = 0;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        restaurant.startDinner();

        for (Programmer programmer : restaurant.getProgrammers()) {
            totalPortionsEaten += programmer.getPortionsEaten();
        }

        assertEquals(numberOfSoupPortions, totalPortionsEaten);
    }

    @Test
    public void startDinner_totalSpoonsTakenIsCorrect() throws InterruptedException {
        int numberOfSoupPortions = 1_000_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 7;
        int numberOfSpoons = 7;
        int portionsEatenLimitDelta = 200;
        int totalSpoonsTaken = 0;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        restaurant.startDinner();

        for (Programmer programmer : restaurant.getProgrammers()) {
            totalSpoonsTaken += programmer.getSpoonsTaken();
        }

        assertEquals(numberOfSoupPortions * (SoupPortion.CAPACITY / Spoon.CAPACITY), totalSpoonsTaken);
    }
}
