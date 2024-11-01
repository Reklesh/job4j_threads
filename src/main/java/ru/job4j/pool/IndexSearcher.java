package ru.job4j.pool;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearcher<T> extends RecursiveTask<Integer> {

    private final List<T> list;
    private final T element;
    private final int from;
    private final int to;

    public IndexSearcher(List<T> list, T element, int from, int to) {
        this.list = list;
        this.element = element;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return findIndex();
        }
        int middle = (from + to) / 2;
        IndexSearcher<T> leftHalf = new IndexSearcher<>(list, element, from, middle);
        IndexSearcher<T> rightHalf = new IndexSearcher<>(list, element, middle + 1, to);
        leftHalf.fork();
        rightHalf.fork();
        int leftResult = leftHalf.join();
        return leftResult != -1 ? leftResult : rightHalf.join();
    }

    private int findIndex() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (Objects.equals(list.get(i), element)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static <T> int find(List<T> list, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new IndexSearcher<>(list, element, 0, list.size() - 1));
    }
}
