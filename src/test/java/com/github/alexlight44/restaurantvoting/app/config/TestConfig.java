package com.github.alexlight44.restaurantvoting.app.config;

import com.github.alexlight44.restaurantvoting.testutil.MutableClock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    MutableClock clock() {
        return new MutableClock();
    }
}
