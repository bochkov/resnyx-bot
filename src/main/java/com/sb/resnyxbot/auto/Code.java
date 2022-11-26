package com.sb.resnyxbot.auto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "region")
public final class Code {

    @Id
    private Integer id;
    private String val;

    @JsonIgnore
    @ManyToOne(targetEntity = Region.class)
    private Region region;
}
