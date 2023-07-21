package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_seat_id", nullable = false, unique = true)
    private ShowSeat showSeat;

    @Column(nullable = false, length = 50)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Reservation() {
    }

    public Reservation(ShowSeat showSeat, String email) {
        nullCheck(showSeat, email);
        validateEmail(email);
        this.showSeat = showSeat;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    private void nullCheck(ShowSeat showSeat, String email) {
        if(showSeat == null) {
            throw new IllegalArgumentException("Reservation show seat must not be null");
        }
        if(email == null) {
            throw new IllegalArgumentException("Reservation email must not be null");
        }
    }

    private void validateEmail(String email) {
        if(emailLengthOutOfRange(email)) {
            throw new IllegalArgumentException("Reservation email length must less than 50");
        }
    }

    private boolean emailLengthOutOfRange(String email) {
        return email.length() > 50;
    }
}
