package com.sb.resnyxbot.qr;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Encode {

    String asBytes(BufferedImage image, String type) throws IOException;

}
