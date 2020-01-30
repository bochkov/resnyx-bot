package com.sb.resnyxbot.qr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public final class SafeEncode implements Encode {

    public static final String SAFE_TYPE = "png";

    private static final List<String> TYPES = Collections.unmodifiableList(
            List.of("png", "jpeg")
    );

    private final Encode origin;

    @Override
    public String asBytes(BufferedImage image, String type) throws IOException {
        String newType = !TYPES.contains(type) ? SAFE_TYPE : type;
        return this.origin.asBytes(image, newType);
    }
}
