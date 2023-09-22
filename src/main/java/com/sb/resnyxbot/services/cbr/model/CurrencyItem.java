package com.sb.resnyxbot.services.cbr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public final class CurrencyItem {

    @Id
    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String id;

    @JsonProperty("Name")
    private String name;

    @Column(name = "eng_name")
    @JsonProperty("EngName")
    private String engName;

    @JsonProperty("Nominal")
    private Integer nominal;

    @Column(name = "parent_code")
    @JsonProperty("ParentCode")
    private String parentCode;

    @Column(name = "iso_num_code")
    @JsonProperty("ISO_Num_Code")
    private Integer isoNumCode;

    @Column(name = "iso_char_code")
    @JsonProperty("ISO_Char_Code")
    private String isoCharCode;

}
