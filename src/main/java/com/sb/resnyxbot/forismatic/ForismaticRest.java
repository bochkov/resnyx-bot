package com.sb.resnyxbot.forismatic;

import com.sb.resnyxbot.common.PushService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cite")
public final class ForismaticRest {

    private final CiteService forismatic;
    private final PushService forismaticResnyx;

    @GetMapping
    public Cite cite() {
        return forismatic.get();
    }

    @PostMapping("/push")
    public void pushCite() {
        forismaticResnyx.push();
    }
}
