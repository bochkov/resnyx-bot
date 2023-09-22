package com.sb.resnyxbot.config;

import java.util.Map;

import kong.unirest.core.*;
import kong.unirest.jackson.JacksonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;

@Slf4j
@Component
@RequiredArgsConstructor
public final class TgBot {

    private final JacksonObjectMapper om;
    private final UnirestInstance unirest;

    public String execute(TgMethod method, String botToken) {
        LOG.info("method = '{}', req = {}, has_input_file = {}", method.methodName(), om.writeValue(method), method.hasInputFile());
        HttpResponse<String> resp;
        HttpRequestWithBody req = unirest.post(String.format("https://api.telegram.org/bot%s/%s", botToken, method.methodName()));
        if (method.hasInputFile()) {
            Map<String, Object> fields = method.toValues(om.getJacksonMapper());
            resp = req.contentType(ContentType.MULTIPART_FORM_DATA.getMimeType())
                    .accept(ContentType.APPLICATION_JSON.getMimeType())
                    .fields(fields)
                    .asString();
        } else {
            resp = req.contentType(ContentType.APPLICATION_JSON.getMimeType())
                    .body(method)
                    .asString();
        }
        String res = resp.getBody();
        LOG.info("{}: raw resp = {}", resp.getStatus(), res);
        return res;
    }

    public <T> T execute(TgMethod method, String botToken, GenericType<T> returnType) {
        String result = execute(method, botToken);
        return om.readValue(result, returnType);
    }
}
