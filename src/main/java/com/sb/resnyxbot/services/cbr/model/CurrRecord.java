package com.sb.resnyxbot.services.cbr.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.sb.resnyxbot.config.CbrValueDeserializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "currency_rate_record")
@NoArgsConstructor
public final class CurrRecord implements Serializable {

    @Id
    @Column
    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;

    @JsonIgnore
    @ManyToOne
    private CurrRate rate;

    @Column(name = "num_code")
    @JsonProperty("NumCode")
    private String numCode;

    @Column(name = "char_code")
    @JsonProperty("CharCode")
    private String charCode;

    @JsonProperty("Nominal")
    private Integer nominal;

    @JsonProperty("Name")
    private String name;

    @Column(name = "rate_value")
    @JsonProperty("Value")
    @JsonDeserialize(using = CbrValueDeserializer.class)
    private BigDecimal value;
}
