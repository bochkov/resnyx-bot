package com.sb.resnyxbot.travis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/travis")
public class TravisRest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Travis travis;

    @Autowired
    public TravisRest(Travis travis) {
        this.travis = travis;
    }

    @PostMapping
    public void travisNotif(String payload) throws IOException {
        travis.send(
                MAPPER.readValue(payload, TravisUpd.class)
        );
    }

    @PostMapping("/force")
    public void travisForce() {
        travis.test();
    }
}
