package com.sb.resnyxbot.auto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@ToString(exclude = "region")
public final class Code {

    @Id
    private Integer id;
    private String value;

    @JsonIgnore
    @ManyToOne(targetEntity = Region.class)
    private Region region;
}
