package com.sb.resnyxbot.services.auto.service;

import java.util.List;

import com.sb.resnyxbot.services.auto.model.Region;

public interface AutoService {

    Region findRegionByCode(String code);

    List<Region> findRegionByName(String name);

}
