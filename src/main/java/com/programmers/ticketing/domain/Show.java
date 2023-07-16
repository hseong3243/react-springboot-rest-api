package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Getter
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    @Column(nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowType showType;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private LocalTime playtime;

    private String description;

    public Show() {

    }

    public Show(String title, ShowType showType, LocalTime playtime, String description) {
        this.title = title;
        this.showType = showType;
        this.playtime = playtime;
        this.description = description;
    }
}
