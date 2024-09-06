package com.petrov.databases.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfiguration {
    private final ZoneId moscowZone = ZoneId.of("Europe/Moscow");

    @Bean
    @Primary
    public Clock clock() {
        return Clock.system(moscowZone);
    }
}
