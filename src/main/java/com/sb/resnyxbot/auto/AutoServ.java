package com.sb.resnyxbot.auto;

import com.sb.resnyxbot.ResnyxService;

public interface AutoServ extends ResnyxService {

    Region findRegionByCode(String code);

    Region findRegionByName(String name);

}
