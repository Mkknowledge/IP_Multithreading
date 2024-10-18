package org.example.thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PracticeCaseStudy {

    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHacker());
        threads.add(new DescendingHacker());
        threads.add(new PoliceThread());

        for (Thread t : threads) {
            t.start();
        }


    }

    private static class Vault {
        Random random = new Random();
        private final int password = random.nextInt(MAX_PASSWORD);

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    private static class AscendingHacker extends Thread {
         Vault vault = new Vault();

        @Override
        public void run() {
            for(int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println("AscendingHacker Thread won. guess -> " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHacker extends Thread {
         Vault vault = new Vault();

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println("DescendingHacker Thread won. guess -> " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for(int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Police arrived.");
            System.exit(0);
        }
    }


}
