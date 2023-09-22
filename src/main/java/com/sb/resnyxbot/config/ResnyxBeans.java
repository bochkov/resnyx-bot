package com.sb.resnyxbot.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kong.unirest.core.Config;
import kong.unirest.core.ObjectMapper;
import kong.unirest.core.UnirestException;
import kong.unirest.core.UnirestInstance;
import kong.unirest.jackson.JacksonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resnyx.util.TgObjectMapperConfig;

@Configuration
public class ResnyxBeans {

    @Bean
    public JacksonObjectMapper unirestObjectMapper() {
        return new JacksonObjectMapper(new TgObjectMapperConfig());
    }

    @Bean
    public UnirestInstance unirest() {
        Config cfg = new Config()
                .setObjectMapper(unirestObjectMapper())
                .followRedirects(true)
                .connectTimeout(5000);
        return new UnirestInstance(cfg);
    }

    @Bean
    public UnirestInstance xmlUnirest() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        ObjectMapper om = new ObjectMapper() {
            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return xmlMapper.readValue(value, valueType);
                } catch (IOException ex) {
                    throw new UnirestException(ex);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return xmlMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new UnirestException(e);
                }
            }
        };
        Config cfg = new Config()
                .setObjectMapper(om)
                .followRedirects(true)
                .connectTimeout(5000);
        return new UnirestInstance(cfg);
    }
}
