package com.sb.resnyxbot.services.qr;

import java.awt.image.BufferedImage;

import com.sb.resnyxbot.services.qr.model.QrRequest;
import com.sb.resnyxbot.services.qr.model.QrResponse;
import com.sb.resnyxbot.services.qr.service.encode.Encode;
import com.sb.resnyxbot.services.qr.service.qrservice.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/qr")
public final class QrRest {

    private final QrService qrService;
    private final Encode encode;

    @PostMapping("/encode")
    public QrResponse encode(@RequestBody QrRequest param) {
        param.validate();
        LOG.info("{}", param);
        try {
            BufferedImage image = qrService.encode(
                    param.getText(),
                    param.getSize(),
                    param.color(),
                    param.logoAsBytes()
            );
            String msg = "";
            if (image == null)
                msg = "Не удалось создать QR-код";
            boolean ok = qrService.isQrCorrect(image, param.getText());
            String code = encode.asBytes(image, param.getType());
            return new QrResponse(code, msg, ok);
        } catch (Exception ex) {
            LOG.warn("", ex);
            return new QrResponse(null, ex.getMessage(), false);
        }
    }
}
