package org.example.thread.creation;

public class ThreadCreation2 {

    /*
    * The second way is instead of creating a separate object for the thread and another object for the runnable,
    * we can directly create a new class that extends thread.
    * */
    public static void main(String[] args) {
        Thread thread = new NewThread();
        thread.start();
    }
    private static class NewThread extends Thread {
        @Override
        public void run() {
            /*
            * By putting our logic inside a class that extends thread.
            * Inside the run method we get access to a lot of data and methods that directly relate to the current thread.
            * */
            System.out.println("Hello from " + this.getName());
        }
    }
}

