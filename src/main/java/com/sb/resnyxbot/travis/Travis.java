package com.sb.resnyxbot.travis;

import com.sb.resnyxbot.forismatic.Forismatic;
import com.sb.resnyxbot.prop.PropRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import resnyx.methods.message.SendMessage;

import java.io.IOException;

@Service
public class Travis {

    private static final Logger LOG = LoggerFactory.getLogger(Forismatic.class);

    private final PropRepo propRepo;

    @Autowired
    public Travis(PropRepo propRepo) {
        this.propRepo = propRepo;
    }

    private void template(String msg) {
        propRepo
                .findById("tg.token")
                .ifPresentOrElse(
                        tok -> propRepo
                                .findById("travis.chat")
                                .ifPresentOrElse(
                                        chat -> {
                                            try {
                                                SendMessage sm = new SendMessage(
                                                        tok.getValue(),
                                                        Long.valueOf(chat.getValue()),
                                                        msg
                                                );
                                                sm.setDisablePreview(true);
                                                sm.setParseMode("Markdown");
                                                sm.execute();
                                            } catch (IOException ex) {
                                                LOG.warn(ex.getMessage(), ex);
                                            }
                                        },
                                        () -> LOG.info("travis.chat is empty. exit")
                                ),
                        () -> LOG.info("tg.token is empty. exit")
                );
    }

    public void test() {
        template("This is travis webhook test: \uD83C\uDDF7\uD83C\uDDFA \uD83D\uDCAA \uD83E\uDD73");
    }

    public void send(TravisUpd payload) {
        template(payload.msg());
    }

}
