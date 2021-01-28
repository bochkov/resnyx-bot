package com.sb.resnyxbot.anekdot;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/anekdot")
public class AnekdotRest {

    @PostMapping("/push")
    public Serializable rest(@RequestParam(required = false) Integer id) {
        return id == null ?
                Anek.random() :
                Anek.byId(id);
    }
}
