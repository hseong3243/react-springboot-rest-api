package com.programmers.ticketing.dto.show;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ShowDto {
    private Long showId;
    private String title;
    private ShowType showType;
    private LocalTime playtime;
    private String description;

    private ShowDto(Long showId, String title, ShowType showType, LocalTime playtime, String description) {
        this.showId = showId;
        this.title = title;
        this.showType = showType;
        this.playtime = playtime;
        this.description = description;
    }

    public static ShowDto from(Show show) {
        return new ShowDto(
                show.getShowId(),
                show.getTitle(),
                show.getShowType(),
                show.getPlaytime(),
                show.getDescription()
        );
    }
}
