package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
public class ShowDto {
    private Long showId;
    private String title;
    private ShowType showType;
    private LocalTime playtime;

    private ShowDto(Long showId, String title, ShowType showType, LocalTime playtime) {
        this.showId = showId;
        this.title = title;
        this.showType = showType;
        this.playtime = playtime;
    }

    public static ShowDto from(Show show) {
        return new ShowDto(
                show.getShowId(),
                show.getTitle(),
                show.getShowType(),
                show.getPlaytime()
        );
    }
}
