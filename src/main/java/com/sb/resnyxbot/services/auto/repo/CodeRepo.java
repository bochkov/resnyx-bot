package com.sb.resnyxbot.services.auto.repo;

import com.sb.resnyxbot.services.auto.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepo extends JpaRepository<Code, Integer> {

    Code findByVal(String code);

}
