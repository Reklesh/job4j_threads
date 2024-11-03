package ru.job4j.pool;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        IntStream.range(0, n)
                .forEach(i -> sums[i] = new Sums(rowSum(matrix, i), colSum(matrix, i)));
        return sums;
    }

    private static int rowSum(int[][] data, int row) {
        return Arrays.stream(data[row])
                .sum();
    }

    private static int colSum(int[][] data, int col) {
        return Arrays.stream(data)
                .mapToInt(a -> a[col])
                .sum();
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, List<CompletableFuture<Integer>>> futures = new HashMap<>();
        IntStream.range(0, n).forEach(i -> futures.putIfAbsent(i, Arrays.asList(
                CompletableFuture.supplyAsync(() -> rowSum(matrix, i)),
                CompletableFuture.supplyAsync(() -> colSum(matrix, i)))
        ));
        for (Integer key : futures.keySet()) {
            sums[key] = new Sums(futures.get(key).get(0).get(), futures.get(key).get(1).get());
        }
        return sums;
    }
}