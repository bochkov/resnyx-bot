package com.sb.resnyxbot;

import com.sb.resnyxbot.forismatic.Forismatic;
import com.sb.resnyxbot.travis.Travis;
import com.sb.resnyxbot.travis.TravisUpd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resnyx.Answer;
import resnyx.TgMethod;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class RestApi {

    private static final Logger LOG = LoggerFactory.getLogger(RestApi.class);

    @PostMapping("/tg")
    public <T> Answer<T> message(@RequestBody TgMethod<T> method) throws IOException {
        LOG.info("received msg {}", method);
        Answer<T> res = method.execute();
        LOG.info("execute complete: {}", res);
        return res;
    }

    @PostMapping("/f/push")
    public void forceForismatic(@Autowired Forismatic forismatic) {
        forismatic.send();
    }

    @PostMapping("/travis")
    public void travisNotif(@RequestBody TravisUpd payload, @Autowired Travis travis) {
        travis.send(payload);
    }

    @PostMapping("/travis/force")
    public void travisForce(@Autowired Travis travis) {
        travis.test();
    }
}
