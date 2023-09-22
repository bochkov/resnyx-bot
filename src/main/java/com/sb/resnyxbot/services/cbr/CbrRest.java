package com.sb.resnyxbot.services.cbr;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.services.cbr.model.CalcRange;
import com.sb.resnyxbot.services.cbr.model.CurrRange;
import com.sb.resnyxbot.services.cbr.model.CurrRate;
import com.sb.resnyxbot.services.cbr.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<CalcRange> ranges(@RequestParam(required = false) String currency) {
        List<String> currencies = currency == null || currency.isEmpty() ?
                List.of("USD", "EUR") :
                Arrays.asList(currency.split(","));
        return cbrService.latestRange(currencies);
    }

    public List<CalcRange> ranges() {
        return cbrService.latestRange(Collections.emptyList());
    }

    @GetMapping("/month/{currency}")
    public CurrRange month(@PathVariable String currency) {
        return cbrService.months(currency, 1);
    }

    @GetMapping("/year/{currency}")
    public CurrRange year(@PathVariable String currency) {
        return cbrService.years(currency, 1);
    }
}
