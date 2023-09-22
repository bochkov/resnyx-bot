package com.sb.resnyxbot.services.qr.service.encode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

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
