package com.sb.resnyxbot.qr;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.util.Base64;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public final class QrRequest implements Serializable {

    public static final Pattern COLOR_PATTERN = Pattern.compile("#[A-Fa-f\\d]{6}");

    private String text;
    private Integer size;
    private String type;
    private String color;
    private String logo;

    public byte[] logoAsBytes() {
        return Base64.getDecoder().decode(logo);
    }

    public Color color() {
        int offset = color.startsWith("#") ? 1 : 0;
        return new Color(
                Integer.valueOf(color.substring(offset, offset + 2), 16),
                Integer.valueOf(color.substring(offset + 2, offset + 4), 16),
                Integer.valueOf(color.substring(offset + 4, offset + 6), 16)
        );
    }

    public void validate() {
        if (logo == null)
            logo = "";
        if (size == null)
            size = SafeQr.SAFE_SIZE;
        if (color == null || color.isEmpty() || !COLOR_PATTERN.matcher(color).matches())
            color = PrettyQr.DEFAULT_COLOR;
        if (type == null || type.isEmpty())
            type = SafeEncode.SAFE_TYPE;
    }
}
