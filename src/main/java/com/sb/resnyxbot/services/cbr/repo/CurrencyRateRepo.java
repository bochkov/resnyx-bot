package com.sb.resnyxbot.services.cbr.repo;

import com.sb.resnyxbot.services.cbr.model.CurrRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepo extends JpaRepository<CurrRate, String> {

    CurrRate findFirstByOrderByDateDesc();

}

