package com.sb.resnyxbot.cbr;

import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cbr")
public final class CbrRest {

    private final CbrService cbrService;
    private final CbrResnyx cbrResnyx;

    @PostMapping("/push")
    public void push() {
        List<CalcRange> ranges = ranges();
        cbrResnyx.push("Курс на " + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(ranges.get(0).date()));
        for (CalcRange range : ranges()) {
            cbrResnyx.push(range.asString());
        }
    }

    @GetMapping("/latest/all")
    public CurrRate rates() {
        return cbrService.latestRates();
    }

    @GetMapping("/latest")
    public List<CalcRange> ranges() {
        return cbrService.latestRange();
    }

    @GetMapping("/month/usd")
    public CurrRange monthUsd() {
        return cbrService.months(Currencies.USD.getCbrCode(), 1);
    }

    @GetMapping("/month/eur")
    public CurrRange monthEur() {
        return cbrService.months(Currencies.EUR.getCbrCode(), 1);
    }

    @GetMapping("/year/usd")
    public CurrRange yearUsd() {
        return cbrService.years(Currencies.USD.getCbrCode(), 1);
    }

    @GetMapping("/year/eur")
    public CurrRange yearEur() {
        return cbrService.years(Currencies.EUR.getCbrCode(), 1);
    }
}
