package com.sb.resnyxbot.cbr;

import java.time.LocalDate;
import java.util.List;

public interface CbrService {

    CurrRate latestRates();

    CurrRange rangeOf(String code, LocalDate dFrom, LocalDate dTo);

    CurrRange days(String code, int days);

    CurrRange months(String code, int months);

    CurrRange years(String code, int years);

    List<CalcRange> latestRange();

}
