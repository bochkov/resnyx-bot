package com.sb.resnyxbot.anekdot;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/anekdot")
public class AnekdotRest {

    @PostMapping("/push")
    public Serializable rest() {
        return Anek.random();
    }
}
