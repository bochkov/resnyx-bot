package com.sb.resnyxbot.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Integer> {

    Code findByValue(String code);

}
