package com.sb.resnyxbot.auto;

import com.sb.resnyxbot.ResnyxService;

import java.util.List;

public interface AutoServ extends ResnyxService {

    Region findRegionByCode(String code);

    List<Region> findRegionByName(String name);

}
