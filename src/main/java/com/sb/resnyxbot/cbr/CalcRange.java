package com.sb.resnyxbot.cbr;

import lombok.Data;

@Data
public final class CalcRange {

    public static final String EUR = "€";
    public static final String USD = "$";

    private final String code;
    private final CurrRange.RangeRecord value;
    private final Double delta;

    public CalcRange(String code, CurrRange.RangeRecord rec0, CurrRange.RangeRecord rec1) {
        this.code = code;
        this.value = rec0;
        this.delta = rec0.getValue().subtract(rec1.getValue()).doubleValue();
    }

    public String asString() {
        return String.format("%S = %.3f ₽ [ %.3f ]",
                code,
                value.getValue(),
                delta
        );
    }
}
