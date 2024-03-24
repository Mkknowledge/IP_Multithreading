package org.example.casestudy.thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaseStudyThreadCreation {

    /*
    * Let's say I want to design a secure vault where I'm planning to store my money and I want to see how long it would take the hackers to break into the volt.
    * By guessing my code, we will have a few hacker threads trying to brute force my code concurrently.
    * In addition to that, we're going to have a police thread. That thread is going to come to our rescue by counting down 10 seconds.
    * And if the hackers haven't broken into the vault by then and ran away with the money, the policemen is going to arrest them, while counting
    * down the police thread is going to show us the progress of its arrival.
    * */
    public static final int MAX_PASSWORD = 9999;
    public static void main(String[] args) {

        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new Police());

        for (Thread thread : threads) {
            thread.start();
        }
    }
    /*
    * So let's start by creating the volt class which holds our password,
    *  which would pass into the class through the constructor.
    * The only method it will have is isCorrectPassword
    * */
    private static class Vault {
        private int password;
        public Vault(int password){
            this.password = password;
        }

        public boolean isCorrectPassword(int guess){
            try {
                /*
                * To slow down the hacker, delay the response by five milliseconds.
                * */
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }


    /*
    * Now using the ability to extend the thread class, we can create a deeper object oriented programming,
    * inheritance hierarchy, where we can encapsulate some common functionality for a group of threads.
    *  So we're going to create an abstract class for a generic hacker thread,
    *
    * */
    private static abstract class HeakerThread extends Thread {
         Vault vault;

         public HeakerThread(Vault vault) {
             this.vault = vault;
             /*
             * Also each concrete hacker thread, which will inherit from the HeakerThread,
             * will have the thread name already set as the class name, and also have its priority set to maximum
             * */
             this.setName(this.getClass().getSimpleName());
             this.setPriority(Thread.MAX_PRIORITY);
         }

         @Override
        public void start() {
             System.out.println("Starting thread " + this.getName());
             super.start();
         }
    }

    /*
    * Creating 1st concrete class which can inherit all functionalities of HeakerThread
    * So let's define the AscendingHackerThread, which extends the hacker thread.
    *  This thread is simply going to guess our password by either rating through all the numbers in an ascending order,
    *  it gets all the HeakerThread and Threat functionality.
    * So the only thing we need to do is to override the run method, to put the specific logic in this class.
    * */

    private static class AscendingHackerThread extends HeakerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }


        /*
        * In the run method, we're just going to iterate from zero to max password.
        *  And in each iteration, we will check if we guessed the password correctly
        *  by calling the isCorrectPassword method on the volt object.
        *  If the guest is correct, we'll print our threads name, which was set in the HackerThreat abstract class and stop the program.
        * */

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++){
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }


    private static class DescendingHackerThread extends HeakerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--){
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    /*
    * The last class we're going to create is the police thread which extends thread directly.
    *  So it doesn't get all the functionality encapsulated in the hacker thread.
    *  All the police thread is going to do is, in run method, it's going to count down from 10 to zero
    * sleeping for one second in between each count.
    *  In each of iteration, it's going to print out how many seconds we have left before it catches the hackers.
    *  And when the 10 seconds elapse, it will catch the hackers and stop the program.
    * */

    private static class Police extends Thread {

        @Override
        public void run() {
            for (int i = 10; i>0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                System.out.println(i);
            }
                System.out.println("Game over for you Hackers");
                System.exit(0);

        }
    }

}
