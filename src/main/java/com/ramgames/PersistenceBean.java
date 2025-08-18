package com.ramgames;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceBean {
    @Bean
    public HashMapPersistence gamePersistence() {
        return new HashMapPersistence();
    }
}
