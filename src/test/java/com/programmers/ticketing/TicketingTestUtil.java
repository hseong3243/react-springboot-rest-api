package com.programmers.ticketing;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.domain.Theater;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketingTestUtil {
    public static List<Show> createShows(int num) {
        List<Show> shows = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Show show = new Show("title" + i, ShowType.CONCERT, LocalTime.of(2, 30), "");
            shows.add(show);
        }
        return shows;
    }

    public static List<Theater> createTheaters(int num) {
        List<Theater> theaters = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Theater theater = new Theater("theater" + i, "address" + i);
            theaters.add(theater);
        }
        return theaters;
    }

    public static Show createShow(String title) {
        return new Show(title, ShowType.CONCERT, LocalTime.of(2, 30), "");
    }

    public static Theater createTheater(String name) {
        return new Theater(name, "address " + name);
    }

    public static ShowInformation createShowInformation(String showTitle, String theaterName, LocalDateTime startTime) {
        Show show = createShow("show");
        Theater theater = createTheater("theater");
        return new ShowInformation(show, theater, startTime);
    }
}
