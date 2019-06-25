package com.sb.resnyxbot.prop;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropRepo extends MongoRepository<Prop, String> {
}
