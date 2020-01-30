package com.sb.resnyxbot.qr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public final class SimpleQr implements QrService {

    @Override
    public BufferedImage encode(String value, Integer size, Color color, byte[] logo) throws WriterException, IOException {
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
}
