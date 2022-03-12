package com.sb.resnyxbot.anekdot;

import com.sb.resnyxbot.common.PushService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/anekdot")
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
