package com.sb.resnyxbot.cbr;

import lombok.Getter;

@Getter
public final class Currencies {

    private final String cbrCode;
    private final String sign;

    private Currencies(String cbrCode, String sign) {
        this.cbrCode = cbrCode;
        this.sign = sign;
    }

    public static final Currencies EUR = new Currencies("R01235", "$");
    public static final Currencies USD = new Currencies("R01239", "€");
}
