package org.example;

public class ThreadUnCaughtException {
    public static void main(String[] args) throws InterruptedException {

        /*
        * Normally unchecked exceptions that happen in Java, simply bring down the entire thread unless we catch them explicitly and handle them in a particular way.
        * With thread.setUncaughtExceptionHandler(), We can set an exception handler for the entire thread at its inception.
        * That handler will be called if an exception was thrown inside the thread and did not get caught anywhere.
        * */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception.");
            }
        });

        thread.setName("Misbehaving thread");

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread " + t.getName()
                + " the error is - " + e.getMessage());
            }
        });

        thread.start();

    }
}