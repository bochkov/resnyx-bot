package com.sb.resnyxbot.qr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrConfig {

    @Bean
    public QrService simpleQr() {
        return new SafeQr(
                new SimpleQr()
        );
    }

    @Bean
    public QrService qrService() {
        return new SafeQr(
                new PrettyQr(
                        simpleQr()
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
