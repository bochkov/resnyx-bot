package com.sb.resnyxbot.cbr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class CurrRange {

    @JsonAlias("ID")
    private String id;

    @JsonAlias("DateRange1")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dFrom;

    @JsonAlias("DateRange2")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dTo;

    private String name;

    @JsonAlias("Record")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RangeRecord> records;

    @Data
    @NoArgsConstructor
    public static final class RangeRecord {

        @JsonAlias("Id")
        private String id;

        @JsonAlias("Date")
        @JsonFormat(pattern = "dd.MM.yyyy")
        private LocalDate date;

        @JsonAlias("Nominal")
        private Integer nominal;

        @JsonAlias("Value")
        private BigDecimal value;
    }
}
