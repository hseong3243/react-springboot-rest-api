package com.programmers.ticketing.dto.seatgrade;

import com.programmers.ticketing.domain.SeatGrade;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SeatGradeDto {
    private final Long seatGradeId;
    private final String name;
    private final LocalDateTime createdAt;

    private SeatGradeDto(Long seatGradeId, String name, LocalDateTime createdAt) {
        this.seatGradeId = seatGradeId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static SeatGradeDto from(SeatGrade seatGrade) {
        return new SeatGradeDto(
                seatGrade.getSeatGradeId(),
                seatGrade.getName(),
                seatGrade.getCreatedAt()
        );
    }
}
