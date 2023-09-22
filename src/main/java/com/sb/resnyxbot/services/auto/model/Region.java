package com.sb.resnyxbot.services.auto.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public final class Region {

    @Id
    private Integer id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private List<Code> codes;
}
