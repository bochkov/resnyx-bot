package com.sb.resnyxbot.anekdot;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public final class Anek implements Serializable {

    private final LocalDateTime date;
    private final String text;

}
