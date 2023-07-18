package com.programmers.ticketing.dto.showinformation;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ShowInformationShowDto {
    private final String title;
    private final LocalTime playtime;
    private final ShowType showType;
    private final String description;

    private ShowInformationShowDto(String title, LocalTime playtime, ShowType showType, String description) {
        this.title = title;
        this.playtime = playtime;
        this.showType = showType;
        this.description = description;
    }

    static ShowInformationShowDto from(Show show) {
        String title = show.getTitle();
        LocalTime playtime = show.getPlaytime();
        ShowType showType = show.getShowType();
        String description = show.getDescription();
        return new ShowInformationShowDto(title, playtime, showType, description);
    }
}
