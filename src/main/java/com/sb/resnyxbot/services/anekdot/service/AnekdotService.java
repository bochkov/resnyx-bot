package com.sb.resnyxbot.services.anekdot.service;

import com.sb.resnyxbot.services.anekdot.model.Anek;

public interface AnekdotService {

    Anek random();

    Anek byId(Integer id);

}
