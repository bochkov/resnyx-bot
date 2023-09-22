package com.sb.resnyxbot.services.qr.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class QrResponse {

    private final String code;
    private final String msg;
    private final boolean ok;

}
