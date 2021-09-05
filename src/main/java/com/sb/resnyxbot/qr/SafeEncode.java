package com.sb.resnyxbot.qr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public final class SafeEncode implements Encode {

    public static final String SAFE_TYPE = "png";

    private static final List<String> TYPES = List.of("png", "jpeg");

    private final Encode origin;

    @Override
    public String asBytes(BufferedImage image, String type) throws IOException {
        String newType = !TYPES.contains(type) ? SAFE_TYPE : type;
        return this.origin.asBytes(image, newType);
    }
}
