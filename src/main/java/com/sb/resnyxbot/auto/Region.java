package com.sb.resnyxbot.auto;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
