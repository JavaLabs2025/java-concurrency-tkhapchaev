package org.labs.dining;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Restaurant {
    private final Semaphore waiters;
    private final Kitchen kitchen;
    private final List<Spoon> spoons;
    private final List<Programmer> programmers;

    public Restaurant(int numberOfSoupPortions, int numberOfWaiters, int numberOfProgrammers, int numberOfSpoons) {
        if (numberOfProgrammers < 2) {
            throw new IllegalArgumentException("numberOfProgrammers >= 2 required");
        }

        if (numberOfSpoons != numberOfProgrammers) {
            throw new IllegalArgumentException("numberOfSpoons = numberOfProgrammers required");
        }

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
            programmers.add(new Programmer(i, left, right, kitchen));
        }
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
        int totalPortions = 0;
        int totalSpoons = 0;

        for (Programmer programmer: programmers) {
            System.out.printf("Programmer [%d]: portions=%d, spoons=%d;\n", programmer.getId(), programmer.getPortionsEaten(), programmer.getSpoonsTaken());
            totalPortions += programmer.getPortionsEaten();
            totalSpoons += programmer.getSpoonsTaken();
        }

        System.out.printf("Total portions eaten: %d (kitchen initialized with %d)\n", totalPortions, kitchen.getNumberOfSoupPortions());
        System.out.printf("Total spoons taken: %d\n", totalSpoons);
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfSoupPortions = 1_000_000;
        int numberOfWaiters = 2;
        int numberOfProgrammers = 7;
        int numberOfSpoons = 7;

        Restaurant restaurant = new Restaurant(numberOfSoupPortions, numberOfWaiters, numberOfProgrammers, numberOfSpoons);
        long dinnerStart = System.currentTimeMillis();
        restaurant.startDinner();
        long dinnerEnd = System.currentTimeMillis();

        System.out.printf("Elapsed: %d ms\n", (dinnerEnd - dinnerStart));
    }
}
