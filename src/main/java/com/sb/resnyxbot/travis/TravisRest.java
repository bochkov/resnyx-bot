package com.sb.resnyxbot.travis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travis")
@RequiredArgsConstructor
public class TravisRest {

    private final TravisResnyx travisResnyx;

    @PostMapping
    public void travisNotif(TravisUpd payload) {
        travisResnyx.push(payload.msg());
    }

    @PostMapping("/push")
    public void travisForce() {
        travisResnyx.push();
    }
}
