package com.sb.resnyxbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import resnyx.Answer;
import resnyx.TgMethod;

import java.io.IOException;

@RestController
public class RestApi {

    private static final Logger LOG = LoggerFactory.getLogger(RestApi.class);

    @PostMapping("/tg")
    public <T> Answer<T> message(@RequestBody TgMethod<T> method) throws IOException {
        LOG.info("received msg {}", method);
        Answer<T> res = method.execute();
        LOG.info("execute complete: {}", res);
        return res;
    }
}
