package com.sb.resnyxbot.travis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public final class Repository {

    private Long id;

    private String name;

    @JsonProperty("owner_name")
    private String owner;

    private String url;
}
