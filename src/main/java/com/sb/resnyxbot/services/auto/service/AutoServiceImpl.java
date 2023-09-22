package com.sb.resnyxbot.services.auto.service;

import java.util.List;

import com.sb.resnyxbot.services.auto.model.Region;
import com.sb.resnyxbot.services.auto.repo.CodeRepo;
import com.sb.resnyxbot.services.auto.repo.RegionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class AutoServiceImpl implements AutoService {

    private final CodeRepo codes;
    private final RegionRepo regions;

    @Override
    public Region findRegionByCode(String code) {
        LOG.info("searching by code = '{}'", code);
        return codes.findByVal(code).getRegion();
    }

    @Override
    public List<Region> findRegionByName(String name) {
        LOG.info("searching by name = '{}'", name);
        return regions.findByNameContainingIgnoreCase(name);
    }

}
