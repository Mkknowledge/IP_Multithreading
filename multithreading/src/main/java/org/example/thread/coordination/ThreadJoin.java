package org.example.thread.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ThreadJoin {

    /*
    * we have a list of numbers that we want to calculate Their factorial.
    * (Factorial of n is the product of all positive descending integers. 4! = 4*3*2*1 = 24)
    *  Factorial calculation is a CPU intensive task, which involves a lot of multiplications.
    *  So we would like to utilize multithreading to delegate the calculation of each number's
    *  factorial to a different thread. This way we can calculate all the numbers factorial in parallel.
    *  So let's look at our factorial calculation thread.
    * */

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(34355L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        // We want to calculate the 0!, 3435!, 35435!, 2324!, 4656!, 23!, 2435!, 5566! factorial's in parallel

        //In the main thread we want to capture all the results from all the factorial threads and print them all out.

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        /*
        *In fact, what we have here is called a race condition between
        *  this line of code- thread.start();, where the factorial threads are started.
        * And this line of code- factorialThread.isFinished() , where the main thread is checking for the results.
        * In other words, the factorial threads and the main thread are racing towards their goals independently.
        *  And we don't know which one will be in which stage by the time the main thread is checking for the results.
        *
        *
        * So let's add the thread, join method in between those lines of code, where the race is happening
        * and the result of that race by forcing the main thread to wait until all the factorial threads are finished.
        * */

        for (Thread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            }else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }


    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long inputNumber) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = inputNumber; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished(){
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
