package com.github.lmartinez84.showtracker.tracker.steps;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrackerAppTest {
    @Test
    void name() {
        assertThat(true).isEqualTo(true);
    }
}