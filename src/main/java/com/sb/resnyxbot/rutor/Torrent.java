package com.sb.resnyxbot.rutor;

import resnyx.TgMethod;
import resnyx.methods.message.SendDocument;
import resnyx.methods.message.SendMessage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Torrent {

    public static final Torrent EMPTY = new Torrent("", "");

    private final String magnetUrl;
    private final String downloadUrl;

    public Torrent(String magnetUrl, String downloadUrl) {
        this.magnetUrl = magnetUrl;
        this.downloadUrl = downloadUrl;
    }

    public List<TgMethod> toTgMethods(String token, Long chatId) {
        if (this.equals(EMPTY)) {
            return Collections.singletonList(
                    new SendMessage(token, chatId,
                            "Не получилось определить документ для скачивания")
            );
        } else {
            return Arrays.asList(
                    new SendDocument(token, chatId, downloadUrl),
                    new SendMessage(token, chatId, magnetUrl)
            );
        }
    }
}
