package com.sb.resnyxbot.qr;

import com.google.zxing.WriterException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface QrService {

    BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException, IOException;

    boolean isQrCorrect(BufferedImage image, String value);

}
