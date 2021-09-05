package com.sb.resnyxbot;

import org.reflections.Reflections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories("com.sb.resnyxbot.prop")
@EnableJpaRepositories("com.sb.resnyxbot.auto")
public class ResnyxBotApplication {

    @Bean
    public Reflections scan() {
        return new Reflections("com.sb.resnyxbot");
    }

    public static void main(String[] args) {
        SpringApplication.run(ResnyxBotApplication.class, args);
    }

}
