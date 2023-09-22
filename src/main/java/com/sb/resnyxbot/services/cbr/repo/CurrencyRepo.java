package com.sb.resnyxbot.services.cbr.repo;

import java.util.List;

import com.sb.resnyxbot.services.cbr.model.CurrencyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyItem, String> {

    CurrencyItem findByIsoCharCodeIgnoreCase(String charCode);

    List<CurrencyItem> findAllByIsoCharCodeIgnoreCaseIn(List<String> chatCode);

}
