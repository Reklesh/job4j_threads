package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    public void checkSum() {
        int[][] data = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Sums[] expect = new Sums[]{new Sums(10, 28), new Sums(26, 32),
                new Sums(42, 36), new Sums(58, 40)};
        Sums[] result = RolColSum.sum(data);
        assertThat(result).isEqualTo(expect);
    }

    @Test
    public void checkAsyncSum() throws ExecutionException, InterruptedException {
        int[][] data = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Sums[] expect = new Sums[]{new Sums(10, 28), new Sums(26, 32),
                new Sums(42, 36), new Sums(58, 40)};
        Sums[] result = RolColSum.asyncSum(data);
        assertThat(result).isEqualTo(expect);
    }
}