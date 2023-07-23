package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Getter
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    @Column(nullable = false, length = 100, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ShowType showType;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private LocalTime playtime;

    @Column(length = 1000)
    private String description;

    private String imagePath;

    public Show() {

    }

    public Show(String title, ShowType showType, LocalTime playtime, String description, String imageName) {
        validateDescription(description);
        this.title = title;
        this.showType = showType;
        this.playtime = playtime;
        this.description = description;
        this.imagePath = imageName;
    }

    public void update(LocalTime playtime, String description, String imagePath) {
        if(playtime != null) {
            this.playtime = playtime;
        }
        if(description != null) {
            validateDescription(description);
            this.description = description;
        }
        if(imagePath != null) {
            this.imagePath = imagePath;
        }
    }

    private void validateDescription(String description) {
        if (isDescriptionLengthOutOfRange(description)) {
            throw new IllegalArgumentException("Show description length must less than 1000");
        }
    }

    private boolean isDescriptionLengthOutOfRange(String description) {
        return description.length() > 1000;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
