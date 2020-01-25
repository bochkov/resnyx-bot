package com.sb.resnyxbot.forismatic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/forismatic")
public class ForismaticRest {

    private final Forismatic forismatic;

    @PostMapping("/push")
    public void forceForismatic() {
        forismatic.send();
    }
}
