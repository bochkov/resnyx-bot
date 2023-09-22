package com.sb.resnyxbot.services.forismatic;

import com.sb.resnyxbot.bot.PushService;
import com.sb.resnyxbot.services.forismatic.model.Cite;
import com.sb.resnyxbot.services.forismatic.service.CiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cite")
@RequiredArgsConstructor
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
