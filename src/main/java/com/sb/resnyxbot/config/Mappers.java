package com.sb.resnyxbot.config;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sb.resnyxbot.config.beans.BigDecimalValueConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mappers {

    @Bean
    public BigDecimalValueConverter bigDecimalValueConverter() {
        return new BigDecimalValueConverter();
    }

    @Bean
    public XmlMapper xmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.findAndRegisterModules();
        SimpleModule custom = new SimpleModule();
        custom.addDeserializer(BigDecimal.class, bigDecimalValueConverter());
        xmlMapper.registerModule(custom);
        return xmlMapper;
    }

    @Bean
    public JsonMapper objectMapper() {
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.findAndRegisterModules();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return jsonMapper;
    }

}
