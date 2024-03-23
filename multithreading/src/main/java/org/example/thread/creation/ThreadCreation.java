package org.example.thread.creation;

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {

        /*
        * In Java, all the threads related properties and methods are encapsulated in the thread class by the JDK.
        * So in order for us to create a new thread, we first need to create a new threat object.
        * The threat object itself is empty by default.
        * So we need to pass it an object of a class that implements the runnable interface into its constructor.
        *  */

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

        /*
        * We need to actually start the thread by calling the start method on the thread object.
        * This will instruct the JVM to create a new thread and pass it to the operating system.
        * */
        thread.start();
        System.out.println("we are in thread: " + Thread.currentThread().getName() + " after starting the new thread.");

        /*
        * The threat class also has a static method called sleep, which puts the current threat to sleep for a given number of milliseconds.
        * It's important to point out that this does not spin in a loop or anything like that.
        * Instead, the threat sleep method, instructs the operating system to not schedule the current thread until that time passes.
        * During that time, this thread is not consuming any CPU.
        * */
        Thread.sleep(10000);
    }
}