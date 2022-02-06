package com.sb.resnyxbot.common;

import resnyx.TgMethod;
import resnyx.model.Message;

import java.util.List;

public interface ResnyxService {

    List<TgMethod<Message>> answer(String token, Message msg);

}
