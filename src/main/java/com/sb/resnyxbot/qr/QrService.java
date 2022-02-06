package com.sb.resnyxbot.qr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.sb.resnyxbot.common.ChooseScope;
import com.sb.resnyxbot.common.ResnyxService;

@ChooseScope(value = "qr")
public interface QrService extends ResnyxService {

    BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException, IOException;

    boolean isQrCorrect(BufferedImage image, String value);

}
