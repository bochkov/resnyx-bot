package com.sb.resnyxbot.auto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auto")
@RequiredArgsConstructor
public final class AutoApi {

    private final AutoServ autoServ;

    @GetMapping("/forCode")
    public Region forCode(@RequestParam String code) {
        return autoServ.findRegionByCode(code);
    }

    @GetMapping("/forRegion")
    public List<Region> forRegion(@RequestParam String region) {
        return autoServ.findRegionByName(region);
    }
}
