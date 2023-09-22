package com.sb.resnyxbot.services.anekdot;

import com.sb.resnyxbot.bot.PushService;
import com.sb.resnyxbot.services.anekdot.model.Anek;
import com.sb.resnyxbot.services.anekdot.service.AnekdotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anekdot")
@RequiredArgsConstructor
public final class AnekdotRest {

    private final AnekdotService anekdotService;
    private final PushService anekdotResnyx;

    @GetMapping
    public Anek rest(@RequestParam(required = false) Integer id) {
        return id == null ?
                anekdotService.random() :
                anekdotService.byId(id);
    }

    @PostMapping("/push")
    public void pushAnek() {
        anekdotResnyx.push();
    }
}
