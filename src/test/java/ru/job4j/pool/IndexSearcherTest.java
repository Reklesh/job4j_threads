package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class IndexSearcherTest {

    @Test
    public void whenElementExistsInSmallRangeForStringThenReturnsCorrectIndex() {
        List<String> list = IntStream.range(1, 11)
                .mapToObj(String::valueOf)
                .toList();
        String str = "10";
        assertThat(IndexSearcher.find(list, str)).isEqualTo(9);
    }

    @Test
    public void whenElementExistsInSmallRangeForIntegerThenReturnsCorrectIndex() {
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .toList();
        Integer integer = 5;
        assertThat(IndexSearcher.find(list, integer)).isEqualTo(4);
    }

    @Test
    public void whenElementExistsInSmallRangeForUserThenReturnsCorrectIndex() {
        List<User> list = IntStream.range(1, 11)
                .mapToObj(i -> new User("Иван" + i, "email" + i))
                .toList();
        User user = new User("Иван7", "email7");
        assertThat(IndexSearcher.find(list, user)).isEqualTo(6);
    }

    @Test
    public void whenElementExistsAtMaxRangeForStringThenReturnsCorrectIndex() {
        List<String> list = IntStream.range(1, 10001)
                .mapToObj(String::valueOf)
                .toList();
        String str = "10000";
        assertThat(IndexSearcher.find(list, str)).isEqualTo(9999);
    }

    @Test
    public void whenElementExistsAtMaxRangeForIntegerThenReturnsCorrectIndex() {
        List<Integer> list = IntStream.range(1, 10001)
                .boxed()
                .toList();
        Integer integer = 5001;
        assertThat(IndexSearcher.find(list, integer)).isEqualTo(5000);
    }

    @Test
    public void whenElementExistsAtMaxRangeForUserThenReturnsCorrectIndex() {
        List<User> list = IntStream.range(1, 10001)
                .mapToObj(i -> new User("Иван" + i, "email" + i))
                .toList();
        User user = new User("Иван7119", "email7119");
        assertThat(IndexSearcher.find(list, user)).isEqualTo(7118);
    }

    @Test
    public void whenElementDoesNotExistForStringThenReturnsMinusOne() {
        List<String> list = IntStream.range(0, 10)
                .mapToObj(String::valueOf)
                .toList();
        String str = "FJP";
        assertThat(IndexSearcher.find(list, str)).isEqualTo(-1);
    }

    @Test
    public void whenElementDoesNotExistForIntegerThenReturnsMinusOne() {
        List<Integer> list = IntStream.range(0, 100)
                .boxed()
                .toList();
        Integer integer = 200;
        assertThat(IndexSearcher.find(list, integer)).isEqualTo(-1);
    }

    @Test
    public void whenElementDoesNotExistForUserThenReturnsMinusOne() {
        List<User> list = IntStream.range(1, 50)
                .mapToObj(i -> new User("Иван" + i, "email" + i))
                .toList();
        User user = new User("Иван", "email");
        assertThat(IndexSearcher.find(list, user)).isEqualTo(-1);
    }
}