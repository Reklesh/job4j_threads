package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        var count = 0;
        var length = process.length;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rload: " + process[count++]);
                if (count == length) {
                    count = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
