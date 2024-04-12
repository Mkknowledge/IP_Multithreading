package org.example.casestudy.thread.creation;

import java.util.ArrayList;
import java.util.List;

public class VaultCaseStudy {

    public static final int MAX_PASSWORD = 9999;
    /*
    Vault
    SecurityGuard
    Hacker_One
    Hacker_Two
    * */

    public static void main(String[] args) {
        Vault vault = new Vault(2121);

        List<Thread> threads = new ArrayList<>();
        threads.add(new Hacker_One(vault));
        threads.add(new Hacker_two(vault));
        threads.add(new SecurityGuard());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            super.start();
        }
    }

    private static class Hacker_One extends HackerThread {
        public Hacker_One(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess ++) {
                if (vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + " guessed the password. " + guess);
                    System.exit(0);
                }
            }
        }
    }


    private static class Hacker_two extends HackerThread {
        public Hacker_two(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + " guessed the password. " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class SecurityGuard extends Thread {
        @Override
        public void run() {
            for (int i = 15; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Caught Hacker");
            System.exit(0);
        }
    }
}
