package com.sb.resnyxbot.services.cbr.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Valuta {

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JsonProperty("Item")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CurrencyItem> items;

}
