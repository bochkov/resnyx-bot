package com.sb.resnyxbot.config;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropRepo extends MongoRepository<Prop, String> {
}
