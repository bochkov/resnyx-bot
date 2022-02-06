package com.sb.resnyxbot.auto;

import com.sb.resnyxbot.common.ResnyxService;

import java.util.List;

public interface AutoService extends ResnyxService {

    Region findRegionByCode(String code);

    List<Region> findRegionByName(String name);

}
