package com.sb.resnyxbot.cbr;

import java.io.Serializable;
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
public final class CurrRate {

    @JsonAlias("Date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    private String name;

    @JsonAlias("Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CurrRecord> currRecords;

    @Data
    @NoArgsConstructor
    public static final class CurrRecord implements Serializable {

        @JsonAlias("ID")
        private String id;

        @JsonAlias("NumCode")
        private String numCode;

        @JsonAlias("CharCode")
        private String charCode;

        @JsonAlias("Nominal")
        private Integer nominal;

        @JsonAlias("Name")
        private String name;

        @JsonAlias("Value")
        private BigDecimal value;
    }
}
