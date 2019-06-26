package com.sb.resnyxbot.travis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travis")
public class TravisRest {

    private final Travis travis;

    @Autowired
    public TravisRest(Travis travis) {
        this.travis = travis;
    }

    @PostMapping("/travis")
    public void travisNotif(@RequestBody TravisUpd payload) {
        travis.send(payload);
    }

    @PostMapping("/travis/force")
    public void travisForce() {
        travis.test();
    }
}
