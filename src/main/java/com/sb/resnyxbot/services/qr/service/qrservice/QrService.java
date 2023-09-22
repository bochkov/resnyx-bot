package com.sb.resnyxbot.services.qr.service.qrservice;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.ResnyxService;

@ChooseScope(value = "qr", desc = "Создание QR-кода")
public interface QrService extends ResnyxService {

    BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException, IOException;

    boolean isQrCorrect(BufferedImage image, String value);

}
