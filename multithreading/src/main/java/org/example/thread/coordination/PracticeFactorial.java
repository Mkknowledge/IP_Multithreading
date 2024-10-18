package org.example.thread.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PracticeFactorial {
    public static void main(String[] args) throws InterruptedException {
        List<Long> numbers = Arrays.asList(40000L, 8L, 3L, 7L);
        List<Factorial> threads = new ArrayList<>();

        for (long numb : numbers) {
            threads.add(new Factorial(numb));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < numbers.size(); i++) {
            Factorial factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + numbers.get(i) +" is " + factorialThread.getResult());
            }else {
                System.out.println("Factorial of " + numbers.get(i) +" is still in progress");

            }
        }


    }

    private static class Factorial extends Thread {
        long numb;
        long tempResult = 1;
        boolean isFinished = false;
        //private BigInteger result = BigInteger.ZERO;

        public Factorial(long numb) {
            this.numb = numb;
        }

        @Override
        public void run() {
            for (long i = numb; i > 0; i--) {
                tempResult = tempResult * i;
                isFinished = true;
            }
        }

        private boolean isFinished() {
            return isFinished;
        }

        private long getResult() {
            return tempResult;
        }

    }
}
