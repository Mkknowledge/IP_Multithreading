package org.example.thread.termination;

import java.io.IOException;

public class QuizDaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(new SleepingThread());
        thread.setName("InputWaitingThread");
        thread.start();
        thread.interrupt();
    }

    private static class WaitingForUserInput implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    char input = (char) System.in.read();
                    if (input == 'q') {
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class SleepingThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
