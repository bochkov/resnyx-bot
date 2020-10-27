package com.sb.resnyxbot.anekdot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public final class Anek implements Serializable {

    private final LocalDateTime date;
    private final String text;

    public String fmtDate() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
