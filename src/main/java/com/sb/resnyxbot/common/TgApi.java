package com.sb.resnyxbot.common;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import resnyx.Answer;
import resnyx.TgMethod;

@Slf4j
@RestController
public final class TgApi {

    @PostMapping("/tg")
    public <T> Answer<T> message(@RequestBody TgMethod<T> method) throws IOException {
        LOG.info("received msg {}", method);
        Answer<T> res = method.execute();
        LOG.info("execute complete: {}", res);
        return res;
    }
}
