package ru.job4j.nonblockingalgoritm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        CASCount casCount = new CASCount();
        var first = new Thread(casCount::increment);
        var second = new Thread(casCount::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get()).isEqualTo(2);
    }
}