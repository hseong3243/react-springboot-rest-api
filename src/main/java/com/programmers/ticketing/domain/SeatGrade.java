package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class SeatGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatGradeId;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public SeatGrade() {

    }

    public SeatGrade(String name) {
        nullCheck(name);
        validateName(name);
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    private void nullCheck(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Seat grade name must not be null");
        }
    }

    private void validateName(String name) {
        if (name.length() > 50) {
            throw new IllegalArgumentException("Seat grade name length must less than 50");
        }
    }
}
