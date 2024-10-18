package org.example.thread.creation;

public class Practice {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Name -> " + Thread.currentThread().getName());
                System.out.println("Current thread Priority " + Thread.currentThread().getPriority());

            }
        });
        System.out.println("Before starting thread, Name -> " + Thread.currentThread().getName());
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        System.out.println("After starting thread, Name -> " + Thread.currentThread().getName());
        Thread.sleep(10000);
    }
}
