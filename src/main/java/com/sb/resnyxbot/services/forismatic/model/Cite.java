package com.sb.resnyxbot.services.forismatic.model;

import lombok.Data;

@Data
public final class Cite {

    private String quoteText;
    private String quoteAuthor;
    private String senderName;
    private String senderLink;
    private String quoteLink;

    public String asString() {
        return quoteText + " " + quoteAuthor;
    }

}
