package com.sb.resnyxbot.cbr;

import java.time.LocalDate;
import java.util.List;

public interface CbrService {

    String USD = "R01235";
    String EUR = "R01239";

    CurrRate latestRates();

    CurrRange rangeOf(String code, LocalDate dFrom, LocalDate dTo);

    List<CalcRange> latestRange();

}
