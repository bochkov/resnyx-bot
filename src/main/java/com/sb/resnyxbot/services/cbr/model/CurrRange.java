package com.sb.resnyxbot.services.cbr.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.sb.resnyxbot.config.CbrValueDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class CurrRange {

    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String id;

    @JacksonXmlProperty(localName = "DateRange1", isAttribute = true)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dFrom;

    @JacksonXmlProperty(localName = "DateRange2", isAttribute = true)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dTo;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JsonProperty("Record")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RangeRecord> records;

    @Data
    @NoArgsConstructor
    public static final class RangeRecord {

        @JacksonXmlProperty(localName = "Id", isAttribute = true)
        private String id;

        @JacksonXmlProperty(localName = "Date", isAttribute = true)
        @JsonFormat(pattern = "dd.MM.yyyy")
        private LocalDate date;

        @JsonProperty("Nominal")
        private Integer nominal;

        @JsonProperty("Value")
        @JsonDeserialize(using = CbrValueDeserializer.class)
        private BigDecimal value;
    }
}
