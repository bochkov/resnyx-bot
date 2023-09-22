package com.sb.resnyxbot.services.rutor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sb.resnyxbot.util.TempFile;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.HttpStatus;
import kong.unirest.core.Unirest;
import resnyx.TgMethod;
import resnyx.common.Caption;
import resnyx.common.InputFile;
import resnyx.messenger.general.SendDocument;
import resnyx.messenger.general.SendMessage;

public final class Torrent {

    public static final Torrent EMPTY = new Torrent("", "");

    private static final Pattern FN = Pattern.compile(".* filename=\"(.*)\"");

    private final String magnetUrl;
    private final String downloadUrl;

    public Torrent(String magnetUrl, String downloadUrl) {
        this.magnetUrl = magnetUrl;
        this.downloadUrl = downloadUrl;
    }

    public List<TgMethod> toTgMethods(String chatId) throws IOException {
        if (this.equals(EMPTY)) {
            return List.of(new SendMessage(chatId, "Не получилось определить документ для скачивания"));
        } else {
            HttpResponse<byte[]> response = Unirest.get(downloadUrl).asBytes();
            if (response.getStatus() != HttpStatus.OK)
                throw new IOException("status = " + response.getStatus() + " " + response.getStatusText());
            byte[] bytes = response.getBody();
            String header = response.getHeaders().getFirst("Content-Disposition");
            Matcher m = FN.matcher(header);
            String name = m.find() ? m.group(1) : "noname";
            File torrent = TempFile.create(name, bytes);

            SendDocument sd = new SendDocument(chatId, new InputFile(torrent));
            sd.setCaption(new Caption(name));
            sd.setDisableContentTypeDetection(true);
            return List.of(
                    new SendMessage(chatId, magnetUrl),
                    sd
            );
        }
    }
}
