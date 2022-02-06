package com.sb.resnyxbot.cbr;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sb.resnyxbot.common.Pair;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public final class XmlCbrService implements CbrService {

    private static final String DAILY_URL = "https://www.cbr.ru/scripts/XML_daily.asp";
    private static final String DYNAMIC_URL = "https://cbr.ru/scripts/XML_dynamic.asp";

    private static final Pair<LocalDateTime, CurrRate> RATES = new Pair<>();

    private final XmlMapper xmlMapper;

    private boolean needUpdate() {
        ZonedDateTime updateMsk = ZonedDateTime.of(
                LocalDate.now(),
                LocalTime.of(12, 0),
                ZoneId.of("Europe/Moscow")
        );
        return RATES.isEmpty()
                || RATES.first().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                || (RATES.second().getDate().isBefore(LocalDate.now()) && RATES.first().isAfter(updateMsk.toLocalDateTime()));
    }

    @SneakyThrows
    @Override
    public CurrRate latestRates() {
        if (needUpdate()) {
            LOG.info("get rates from cbr server");
            RestTemplate rest = new RestTemplate();
            UriComponents uris = UriComponentsBuilder.fromHttpUrl(DAILY_URL)
                    .build();
            ResponseEntity<String> response = rest.getForEntity(uris.toUriString(), String.class);
            if (response.getStatusCode() != HttpStatus.OK)
                throw new RestClientException("status = " + response.getStatusCode());
            CurrRate rates = xmlMapper.readValue(response.getBody(), CurrRate.class);
            RATES.replace(LocalDateTime.now(), rates);
        }
        return RATES.second();
    }

    @SneakyThrows
    @Override
    public CurrRange rangeOf(String code, LocalDate dFrom, LocalDate dTo) {
        RestTemplate rest = new RestTemplate();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        UriComponents uris = UriComponentsBuilder.fromHttpUrl(DYNAMIC_URL)
                .queryParam("date_req1", dFrom.format(fmt))
                .queryParam("date_req2", dTo.format(fmt))
                .queryParam("VAL_NM_RQ", code)
                .build();
        LOG.info(uris.toUriString());
        ResponseEntity<String> response = rest.getForEntity(uris.toUriString(), String.class);
        if (response.getStatusCode() != HttpStatus.OK)
            throw new RestClientException("status = " + response.getStatusCode());
        return xmlMapper.readValue(response.getBody(), CurrRange.class);
    }

    @Override
    public List<CalcRange> latestRange() {
        CurrRange usd = rangeOf(CbrService.USD, LocalDate.now().minusMonths(1), LocalDate.now());
        List<CurrRange.RangeRecord> rec0 = usd.getRecords();
        rec0.sort(Comparator.comparing(CurrRange.RangeRecord::getDate).reversed());
        //
        CurrRange eur = rangeOf(CbrService.EUR, LocalDate.now().minusMonths(1), LocalDate.now());
        List<CurrRange.RangeRecord> rec1 = eur.getRecords();
        rec1.sort(Comparator.comparing(CurrRange.RangeRecord::getDate).reversed());
        return Arrays.asList(
                new CalcRange(CalcRange.USD, rec0.get(0), rec0.get(1)),
                new CalcRange(CalcRange.EUR, rec1.get(0), rec1.get(1))
        );
    }
}
