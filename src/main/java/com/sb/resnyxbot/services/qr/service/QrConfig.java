package com.sb.resnyxbot.services.qr.service;

import com.sb.resnyxbot.services.qr.service.encode.Encode;
import com.sb.resnyxbot.services.qr.service.encode.SafeEncode;
import com.sb.resnyxbot.services.qr.service.encode.SimpleEncode;
import com.sb.resnyxbot.services.qr.service.qrservice.PrettyQr;
import com.sb.resnyxbot.services.qr.service.qrservice.QrService;
import com.sb.resnyxbot.services.qr.service.qrservice.SafeQr;
import com.sb.resnyxbot.services.qr.service.qrservice.SimpleQr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrConfig {

    @Bean
    public QrService qrService() {
        return new SafeQr(
                new PrettyQr(
                        new SimpleQr()
                )
        );
    }

    @Bean
    public Encode encode() {
        return new SafeEncode(
                new SimpleEncode()
        );
    }
}
