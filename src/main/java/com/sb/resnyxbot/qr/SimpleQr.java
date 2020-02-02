package com.sb.resnyxbot.qr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import resnyx.TgMethod;
import resnyx.methods.message.SendPhoto;
import resnyx.model.InputFile;
import resnyx.model.Message;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class SimpleQr implements QrService {

    @Override
    public BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = writer.encode(value, BarcodeFormat.QR_CODE, size, size, hints);

        int matrixSize = matrix.getWidth();
        BufferedImage image = new BufferedImage(matrixSize, matrixSize, BufferedImage.TYPE_INT_ARGB);
        image.createGraphics();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(color);

        for (int i = 0; i < matrixSize; ++i) {
            for (int j = 0; j < matrixSize; ++j) {
                if (matrix.get(i, j)) {
                    g2.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }

    @Override
    public boolean isQrCorrect(BufferedImage image, String value) {
        Result qrResult = null;
        if (image != null) {
            try {
                qrResult = new MultiFormatReader().decode(
                        new BinaryBitmap(
                                new HybridBinarizer(
                                        new BufferedImageLuminanceSource(image)
                                )
                        )
                );
            } catch (NotFoundException ex) {
                log.info("", ex);
            }
        }
        return qrResult != null && value != null && value.equals(qrResult.getText());
    }

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        String text = msg.getText();
        String value = text.substring(text.indexOf(' ') + 1);
        try {
            // bufferedimage to png
            BufferedImage image = encode(value, 500, Color.BLACK, new byte[]{});
            byte[] buffer;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                ImageIO.write(image, "png", out);
                buffer = out.toByteArray();
            }
            // save png to send message
            return List.of(
                    new SendPhoto(
                            token,
                            msg.getChat().getId(),
                            new InputFile(buffer, "qr.png")
                    )
            );
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }
}
