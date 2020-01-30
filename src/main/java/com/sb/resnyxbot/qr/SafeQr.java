package com.sb.resnyxbot.qr;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public final class SafeQr implements QrService {

    public static final Integer SAFE_SIZE = 750;

    private final QrService origin;

    @Override
    public BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException, IOException {
        Integer newSize = size > SAFE_SIZE ? SAFE_SIZE : size;
        return this.origin.encode(value, newSize, color, logo);
    }

    @Override
    public boolean isQrCorrect(BufferedImage image, String value) {
        return origin.isQrCorrect(image, value);
    }
}
