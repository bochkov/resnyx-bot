package com.sb.resnyxbot.services.forismatic.service;

import com.sb.resnyxbot.services.forismatic.model.Cite;
import kong.unirest.core.Unirest;
import org.springframework.stereotype.Service;

@Service
public final class ForismaticService implements CiteService {

    private static final String API_URL = "https://api.forismatic.com/api/1.0/";

    @Override
    public Cite get() {
        return Unirest.get(API_URL)
                .queryString("method", "getQuote")
                .queryString("format", "json")
                .queryString("lang", "ru")
                .asObject(Cite.class)
                .getBody();
    }
}
