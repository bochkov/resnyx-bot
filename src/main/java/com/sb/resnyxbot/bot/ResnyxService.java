package com.sb.resnyxbot.bot;

import java.util.List;

import resnyx.TgMethod;
import resnyx.messenger.general.Message;

public interface ResnyxService {

    List<TgMethod> answer(Message msg);

}
