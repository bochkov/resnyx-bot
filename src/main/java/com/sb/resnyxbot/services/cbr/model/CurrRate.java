package com.sb.resnyxbot.services.cbr.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "currency_rate")
@NoArgsConstructor
public final class CurrRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @JsonIgnore
    @Column(name = "fetch_time")
    @CreationTimestamp
    private LocalDateTime fetchTime;

    private String name;

    @JsonProperty("Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_id")
    private List<CurrRecord> currRecords;

}
