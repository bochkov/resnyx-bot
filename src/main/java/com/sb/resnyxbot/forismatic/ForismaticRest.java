package com.sb.resnyxbot.forismatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forismatic")
public class ForismaticRest {

    private final Forismatic forismatic;

    @Autowired
    public ForismaticRest(Forismatic forismatic) {
        this.forismatic = forismatic;
    }

    @PostMapping("/push")
    public void forceForismatic() {
        forismatic.send();
    }
}
