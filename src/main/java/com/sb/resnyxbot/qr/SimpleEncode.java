package com.sb.resnyxbot.qr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class SimpleEncode implements Encode {
    @Override
    public String asBytes(BufferedImage image, String type) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, type, out);
            byte[] imageBytes = out.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
