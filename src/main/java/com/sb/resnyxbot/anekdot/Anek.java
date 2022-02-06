package com.sb.resnyxbot.anekdot;

import java.io.Serializable;

import lombok.Data;

@Data
public final class Anek implements Serializable {

    private final String id;
    private final String text;

}
