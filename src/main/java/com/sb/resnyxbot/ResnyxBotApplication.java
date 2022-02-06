package com.sb.resnyxbot;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories("com.sb.resnyxbot.prop")
@EnableJpaRepositories("com.sb.resnyxbot.auto")
public class ResnyxBotApplication {

    @Bean
    public XmlMapper xmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.findAndRegisterModules();
        SimpleModule custom = new SimpleModule();
        custom.addDeserializer(BigDecimal.class, new BigDecimalValueConverter());
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

    public static final class BigDecimalValueConverter extends StdDeserializer<BigDecimal> {

        public BigDecimalValueConverter() {
            super(BigDecimal.class);
        }

        @Override
        public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new BigDecimal(p.getText().replace(",", "."));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ResnyxBotApplication.class, args);
    }

}
