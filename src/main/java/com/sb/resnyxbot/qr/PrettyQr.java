package com.sb.resnyxbot.qr;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import resnyx.TgMethod;
import resnyx.model.Message;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public final class PrettyQr implements QrService {

    public static final String DEFAULT_COLOR = "#000000";

    private final QrService simpleQr;

    @Override
    public BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException, IOException {
        BufferedImage image = simpleQr.encode(value, size, color, logo);
        return (logo == null || logo.length == 0) ?
                image : generatePretty(image, logo);
    }

    @Override
    public boolean isQrCorrect(BufferedImage image, String value) {
        return simpleQr.isQrCorrect(image, value);
    }

    private BufferedImage generatePretty(BufferedImage image, byte[] bytes) throws IOException {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        BufferedImage logo = ImageIO.read(new ByteArrayInputStream(bytes));
        double scale = scaleRate(image, logo);
        logo = scaledImage(logo, (int) (logo.getWidth() * scale), (int) (logo.getHeight() * scale));
        g2.setBackground(new Color(0, 0, 0, 0));
        g2.clearRect(
                image.getWidth() / 2 - logo.getWidth() / 2 - 5,
                image.getHeight() / 2 - logo.getHeight() / 2 - 5,
                logo.getWidth() + 10,
                logo.getHeight() + 10
        );
        g2.drawImage(logo,
                image.getWidth() / 2 - logo.getWidth() / 2,
                image.getHeight() / 2 - logo.getHeight() / 2,
                image.getWidth() / 2 + logo.getWidth() / 2,
                image.getHeight() / 2 + logo.getHeight() / 2,
                0, 0, logo.getWidth(), logo.getHeight(), null
        );
        return image;
    }

    private double scaleRate(BufferedImage image, BufferedImage logo) {
        double rate = logo.getWidth() / (double) image.getWidth();
        return rate > 0.3 ? 0.3 : 1;
    }

    private BufferedImage scaledImage(BufferedImage image, int width, int height) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scale = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp scaleOp = new AffineTransformOp(scale, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(image, new BufferedImage(width, height, image.getType()));
    }

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return simpleQr.answer(token, msg);
    }
}
