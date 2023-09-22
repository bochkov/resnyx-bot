package com.sb.resnyxbot.services.cbr.service;

import java.time.LocalDate;
import java.util.List;

import com.sb.resnyxbot.services.cbr.model.CalcRange;
import com.sb.resnyxbot.services.cbr.model.CurrRange;
import com.sb.resnyxbot.services.cbr.model.CurrRate;

public interface CbrService {

    CurrRate latestRates();

    CurrRange rangeOf(String code, LocalDate dFrom, LocalDate dTo);

    CurrRange days(String code, int days);

    CurrRange months(String code, int months);

    CurrRange years(String code, int years);

    List<CalcRange> latestRange(List<String> curCodes);

}
