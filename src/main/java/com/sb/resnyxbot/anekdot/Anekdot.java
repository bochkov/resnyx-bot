package com.sb.resnyxbot.anekdot;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.sb.resnyxbot.ResnyxService;
import com.sb.resnyxbot.prop.Prop;
import com.sb.resnyxbot.prop.PropRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public final class Anekdot implements ResnyxService {

    private final PropRepo propRepo;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return Collections.singletonList(
                new SendMessage(token, msg.getChat().getId(), Anek.random().text())
        );
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void send() {
        Optional<Prop> tgToken = propRepo.findById("tg.token");
        Optional<Prop> chatId = propRepo.findById("chat.auto.send");
        if (tgToken.isEmpty()) {
            LOG.info("tg.token is empty. exit");
            return;
        }
        if (chatId.isEmpty()) {
            LOG.info("chat.auto.send is empty. exit");
            return;
        }
        for (String chId : chatId.get().getValue().split(";")) {
            try {
                String text = String.format("Анекдот дня:%n%s", Anek.random().text());
                new SendMessage(
                        tgToken.get().getValue(),
                        Long.valueOf(chId),
                        text
                ).execute();
            } catch (IOException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
    }
}
