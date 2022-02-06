package com.sb.resnyxbot.travis;

import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.common.PushResnyx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravisResnyx extends PushResnyx {

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return Collections.emptyList();
    }

    @Override
    protected String pushText() {
        return "This is travis webhook test: \uD83C\uDDF7\uD83C\uDDFA \uD83D\uDCAA \uD83E\uDD73";
    }

    @Override
    protected void configure(SendMessage answer) {
        answer.setDisablePreview(true);
        answer.setParseMode("Markdown");
    }
}
