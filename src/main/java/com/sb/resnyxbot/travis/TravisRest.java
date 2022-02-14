package com.sb.resnyxbot.travis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/travis")
@RequiredArgsConstructor
public final class TravisRest {

    private final TravisResnyx travisResnyx;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void travisNotif(TravisUpd payload) {
        travisResnyx.push(payload.msg());
    }

    @PostMapping("/push")
    public void travisForce() {
        travisResnyx.push();
    }
}
