package ru.job4j.pool;

import ru.job4j.waitnotifynotifyall.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        IntStream.range(0, size).forEach(i -> {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        });
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
