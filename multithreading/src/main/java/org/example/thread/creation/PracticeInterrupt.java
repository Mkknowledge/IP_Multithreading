package org.example.thread.creation;

import java.math.BigDecimal;

public class PracticeInterrupt {
    public static void main(String[] args) {
        Thread t = new Thread(new LongComputation(new BigDecimal(200000), new BigDecimal(457854)));
        t.start();
    }

    private static class LongComputation implements Runnable {
        private final BigDecimal base;
        private final BigDecimal power;

        public LongComputation(BigDecimal base, BigDecimal power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + " ^ " + power + " = " + pow(base, power));
        }

        private BigDecimal pow(BigDecimal base, BigDecimal power) {
            BigDecimal result = BigDecimal.ONE;

            for (BigDecimal i = BigDecimal.ZERO; i.compareTo(power) != 0; i.add(BigDecimal.ONE)) {
                result = result.multiply(base);
            }

            return result;
        }
    }
}
