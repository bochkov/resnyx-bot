package com.sb.resnyxbot.forismatic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public final class ForismaticService implements CiteService {

    private static final String API_URL = "https://api.forismatic.com/api/1.0/";

    @Override
    public Cite get() {
        RestTemplate rest = new RestTemplate();
        UriComponents uris = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("method", "getQuote")
                .queryParam("format", "json")
                .queryParam("lang", "ru")
                .build();
        ResponseEntity<Cite> response = rest.getForEntity(uris.toUriString(), Cite.class);
        if (response.getStatusCode() != HttpStatus.OK)
            throw new RestClientException("status = " + response.getStatusCode());
        return response.getBody();
    }
}
