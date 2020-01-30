package com.sb.resnyxbot.qr;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class QrResponse {

    private final String code;
    private final String msg;
    private final boolean ok;

}
