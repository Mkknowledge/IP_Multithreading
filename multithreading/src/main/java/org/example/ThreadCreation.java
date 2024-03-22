package org.example;

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("we are now in thread: " + Thread.currentThread().getName());
                System.out.println("Current thread Priority " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("we are in thread: " + Thread.currentThread().getName() + " before starting the new thread.");
        thread.start();
        System.out.println("we are in thread: " + Thread.currentThread().getName() + " after starting the new thread.");

        //Thread.sleep(10000);
    }
}