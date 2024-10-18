package org.example.thread.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfFactorialDuplicate {

    public static void main(String[] args) throws InterruptedException {

        List<Long> inputNumbers = Arrays.asList(1545454545454540L, 234L, 34L, 564L, 23434L, 43L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputnumber : inputNumbers) {
            threads.add(new FactorialThread(inputnumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread: threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()){
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            }else {
                System.out.println("the calculation for " + inputNumbers.get(i) + " is still in progress.");
            }
        }

    }

    public static class FactorialThread extends Thread {
        private long inputNumber;

        private BigInteger result = BigInteger.ZERO;

        private boolean isFinished = false;

        public FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(Long inputNumber) {
            BigInteger tempResult = BigInteger.ONE;

           for(long i = inputNumber; i > 0; i--){
               tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
           }
           return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult()  {
            return result;
        }

    }
}
