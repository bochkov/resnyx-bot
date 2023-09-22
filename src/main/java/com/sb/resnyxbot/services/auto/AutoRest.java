package com.sb.resnyxbot.services.auto;

import java.util.List;

import com.sb.resnyxbot.services.auto.model.Region;
import com.sb.resnyxbot.services.auto.service.AutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto")
@RequiredArgsConstructor
public final class AutoRest {

    private final AutoService autoServ;

    @GetMapping("/forCode")
    public Region forCode(@RequestParam String code) {
        return autoServ.findRegionByCode(code);
    }

    @GetMapping("/forRegion")
    public List<Region> forRegion(@RequestParam String region) {
        return autoServ.findRegionByName(region);
    }
}
