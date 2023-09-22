package com.sb.resnyxbot.services.cbr.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sb.resnyxbot.services.cbr.model.*;
import com.sb.resnyxbot.services.cbr.repo.CurrencyRateRepo;
import com.sb.resnyxbot.services.cbr.repo.CurrencyRepo;
import jakarta.annotation.PostConstruct;
import kong.unirest.core.UnirestInstance;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class XmlCbrService implements CbrService {

    private static final String CURRENCY_URL = "https://www.cbr.ru/scripts/XML_valFull.asp";
    private static final String DAILY_URL = "https://cbr.ru/scripts/XML_daily.asp";
    private static final String DYNAMIC_URL = "https://cbr.ru/scripts/XML_dynamic.asp";

    private final UnirestInstance xmlUnirest;
    private final CurrencyRepo currencyRepo;
    private final CurrencyRateRepo currencyRateRepo;

    @SneakyThrows
    @PostConstruct
    public void fetchData() {
        fetchAndSaveCurrencies();
        fetchAndSaveCurrRates();
    }

    private void fetchAndSaveCurrencies() {
        LOG.info("fetching currencies");
        Valuta valuta = xmlUnirest.get(CURRENCY_URL)
                .asObject(Valuta.class)
                .getBody();
        currencyRepo.saveAll(valuta.getItems());
    }

    private void fetchAndSaveCurrRates() {
        LOG.info("fetching daily currency rates");
        CurrRate rates = xmlUnirest.get(DAILY_URL)
                .asObject(CurrRate.class)
                .getBody();
        currencyRateRepo.save(rates);
    }

    private boolean needUpdate() {
        CurrRate latestRate = currencyRateRepo.findFirstByOrderByDateDesc();
        ZonedDateTime updateMsk = ZonedDateTime.of(
                LocalDate.now(),
                LocalTime.of(12, 0),
                ZoneId.of("Europe/Moscow")
        );
        return latestRate == null ||
                latestRate.getFetchTime().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)) ||
                (latestRate.getDate().isBefore(LocalDate.now()) && latestRate.getFetchTime().isAfter(updateMsk.toLocalDateTime()));
    }

    @SneakyThrows
    @Override
    public CurrRate latestRates() {
        if (needUpdate()) {
            fetchAndSaveCurrRates();
        }
        return currencyRateRepo.findFirstByOrderByDateDesc();
    }

    @SneakyThrows
    @Override
    public CurrRange rangeOf(String code, LocalDate dFrom, LocalDate dTo) {
        CurrencyItem item = currencyRepo.findByIsoCharCodeIgnoreCase(code);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return xmlUnirest.get(DYNAMIC_URL)
                .queryString("date_req1", dFrom.format(fmt))
                .queryString("date_req2", dTo.format(fmt))
                .queryString("VAL_NM_RQ", item.getId())
                .asObject(CurrRange.class)
                .getBody();
    }

    @Override
    public CurrRange days(String code, int days) {
        return rangeOf(
                code,
                LocalDate.now().minusDays(days),
                LocalDate.now().plusDays(1)
        );
    }

    @Override
    public CurrRange months(String code, int months) {
        return rangeOf(
                code,
                LocalDate.now().minusMonths(months),
                LocalDate.now().plusDays(1)
        );
    }

    @Override
    public CurrRange years(String code, int years) {
        return rangeOf(
                code,
                LocalDate.now().minusYears(years),
                LocalDate.now().plusDays(1)
        );
    }

    @Override
    public List<CalcRange> latestRange(List<String> currencies) {
        List<CurrencyItem> currs = currencyRepo.findAllByIsoCharCodeIgnoreCaseIn(currencies);
        List<CalcRange> ranges = new ArrayList<>();
        for (CurrencyItem curr : currs) {
            CurrRange range = rangeOf(
                    curr.getIsoCharCode(),
                    LocalDate.now().minusDays(14),
                    LocalDate.now().plusDays(1)
            );
            List<CurrRange.RangeRecord> recs = range.getRecords();
            recs.sort(Comparator.comparing(CurrRange.RangeRecord::getDate).reversed());
            ranges.add(new CalcRange(curr.getIsoCharCode(), recs.get(0), recs.get(1)));
        }
        return ranges;
    }
}
