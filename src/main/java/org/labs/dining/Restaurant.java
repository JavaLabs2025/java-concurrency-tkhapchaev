package org.labs.dining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Restaurant {
    private final Semaphore waiters;
    private final Kitchen kitchen;
    private final List<Spoon> spoons;
    private final List<Programmer> programmers;

    public Restaurant(int numberOfSoupPortions, int numberOfWaiters, int numberOfProgrammers, int numberOfSpoons, int portionsEatenLimitDelta) {
        if (numberOfProgrammers < 2) {
            throw new IllegalArgumentException("numberOfProgrammers >= 2 required");
        }

        if (numberOfSpoons != numberOfProgrammers) {
            throw new IllegalArgumentException("numberOfSpoons = numberOfProgrammers required");
        }

        if (numberOfWaiters < 2) {
            throw new IllegalArgumentException("numberOfWaiters >= 2 required");
        }

        int numberOfPortionsPerProgrammer = Math.round((float)numberOfSoupPortions / (float)numberOfProgrammers);

        waiters = new Semaphore(numberOfWaiters, true);
        kitchen = new Kitchen(numberOfSoupPortions, waiters);

        spoons = new ArrayList<>();
        programmers = new ArrayList<>();

        for (int i = 0; i < numberOfSpoons; i++) {
            spoons.add(new Spoon(i));
        }

        for (int i = 0; i < numberOfProgrammers; i++) {
            Spoon left = spoons.get((i + 1) % numberOfSpoons);
            Spoon right = spoons.get(i);
            programmers.add(new Programmer(i, left, right, kitchen, numberOfPortionsPerProgrammer + portionsEatenLimitDelta));
        }
    }

    public List<Spoon> getSpoons() {
        return Collections.unmodifiableList(spoons);
    }

    public List<Programmer> getProgrammers() {
        return Collections.unmodifiableList(programmers);
    }

    public void startDinner() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        System.out.println("--- Dinner started ---");

        for (Programmer p : programmers) {
            Thread thread = new Thread(p, "Programmer [" + p.getId() + "]");
            threads.add(thread);
            thread.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("--- Dinner finished ---");
        int totalPortionsEaten = 0;
        int totalSpoonsTaken = 0;

        for (Programmer programmer: programmers) {
            System.out.printf("Programmer [%d]: portions=%d, spoons=%d;\n", programmer.getId(), programmer.getPortionsEaten(), programmer.getSpoonsTaken());
            totalPortionsEaten += programmer.getPortionsEaten();
            totalSpoonsTaken += programmer.getSpoonsTaken();
        }

        System.out.printf("Total portions eaten: %d (kitchen initialized with %d)\n", totalPortionsEaten, kitchen.getNumberOfSoupPortions());
        System.out.printf("Total spoons taken: %d\n", totalSpoonsTaken);
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfSoupPortions = 1_000_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 7;
        int numberOfSpoons = 7;
        int portionsEatenLimitDelta = 200;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons, portionsEatenLimitDelta);
        long dinnerStart = System.currentTimeMillis();
        restaurant.startDinner();
        long dinnerEnd = System.currentTimeMillis();

        System.out.printf("Elapsed: %d ms\n", (dinnerEnd - dinnerStart));
    }
}
