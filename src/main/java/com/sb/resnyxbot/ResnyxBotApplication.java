package com.sb.resnyxbot;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories("com.sb.resnyxbot.prop")
@EnableJpaRepositories("com.sb.resnyxbot.auto")
public class ResnyxBotApplication {

    public static void main(String[] args) {
        Unirest.config()
                .followRedirects(true)
                .socketTimeout(10000)
                .connectTimeout(10000);
        SpringApplication.run(ResnyxBotApplication.class, args);
    }

}
