package com.sb.resnyxbot.rutor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import resnyx.TgMethod;
import resnyx.methods.message.SendDocument;
import resnyx.methods.message.SendMessage;
import resnyx.model.InputFile;
import resnyx.model.Message;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Torrent {

    public static final Torrent EMPTY = new Torrent("", "");

    private static final Pattern FN = Pattern.compile(".* filename=\"(.*)\"");

    private final String magnetUrl;
    private final String downloadUrl;

    public Torrent(String magnetUrl, String downloadUrl) {
        this.magnetUrl = magnetUrl;
        this.downloadUrl = downloadUrl;
    }

    public List<TgMethod<Message>> toTgMethods(String token, Long chatId) throws IOException {
        if (this.equals(EMPTY)) {
            return Collections.singletonList(
                    new SendMessage(token, chatId,
                            "Не получилось определить документ для скачивания")
            );
        } else {
            ResponseEntity<byte[]> response = new RestTemplate().getForEntity(downloadUrl, byte[].class);
            if (response.getStatusCode() != HttpStatus.OK)
                throw new IOException("status = " + response.getStatusCode());
            byte[] bytes = response.getBody();
            String header = response.getHeaders()
                    .computeIfAbsent("Content-Disposition", s -> Collections.singletonList(""))
                    .get(0);
            Matcher m = FN.matcher(header);
            String name = m.find() ? m.group(1) : "noname";
            return Arrays.asList(
                    new SendMessage(token, chatId, magnetUrl),
                    new SendDocument(token, chatId, new InputFile(bytes, name))
            );
        }
    }
}
