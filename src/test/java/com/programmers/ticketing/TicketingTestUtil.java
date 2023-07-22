package com.programmers.ticketing;

import com.programmers.ticketing.domain.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketingTestUtil {
    private static final String DEFAULT_SEAT_GRADE = "vip";
    private static final ShowType DEFAULT_SHOW_TYPE = ShowType.CONCERT;
    private static final int DEFAULT_FEE = 1000;
    private static final LocalDateTime DEFAULT_START_TIME = LocalDateTime.now().plusHours(1);
    private static final String DEFAULT_THEATER_NAME = "theater";
    private static final String DEFAULT_SHOW_TITLE = "title";
    private static final String DEFAULT_EMAIL = "email@gmail.com";
    private static final SeatPosition DEFAULT_SEAT_POSITION = new SeatPosition(1, 1, 1);

    public static List<Show> createShows(int num) {
        List<Show> shows = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Show show = new Show("title" + i, ShowType.CONCERT, LocalTime.of(2, 30), "", null);
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
        return new Show(title, ShowType.CONCERT, LocalTime.of(2, 30), "", null);
    }

    public static Theater createTheater(String name) {
        return new Theater(name, "address " + name);
    }

    public static ShowInformation createShowInformation(String showTitle, String theaterName, LocalDateTime startTime) {
        Show show = createShow("show");
        Theater theater = createTheater("theater");
        return new ShowInformation(show, theater, startTime);
    }

    public static Seat createSeat(String theaterName, int position) {
        Theater theater = createTheater(theaterName);
        SeatPosition seatPosition = new SeatPosition(position, position, position);
        return new Seat(theater, seatPosition);
    }

    public static Seat createSeat(Theater theater, int position) {
        SeatPosition seatPosition = new SeatPosition(position, position, position);
        return new Seat(theater, seatPosition);
    }

    public static ShowSeat createShowSeat() {
        Theater theater = createTheater(DEFAULT_THEATER_NAME);
        Show show = createShow(DEFAULT_SHOW_TITLE);
        ShowInformation showInformation = createShowInformation(theater, show);
        SeatGrade seatGrade = new SeatGrade(DEFAULT_SEAT_GRADE);
        Seat seat = createSeat(theater, 1);
        return new ShowSeat(showInformation, seat, seatGrade, DEFAULT_FEE);
    }

    public static List<Seat> createSeats(Theater theater, int seatCount) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < seatCount; i++) {
            Seat seat = createSeat(theater, 50);
            seats.add(seat);
        }
        return seats;
    }

    public static ShowInformation createShowInformation(Theater theater, Show show) {
        return new ShowInformation(show, theater, DEFAULT_START_TIME);
    }

    public static List<ShowSeat> createShowSeats(int seatCount) {
        List<ShowSeat> showSeats = new ArrayList<>();
        Theater theater = createTheater(DEFAULT_THEATER_NAME);
        Show show = createShow(DEFAULT_SHOW_TITLE);
        ShowInformation showInformation = createShowInformation(theater, show);
        SeatGrade seatGrade = new SeatGrade(DEFAULT_SEAT_GRADE);
        for (int i = 0; i < seatCount; i++) {
            Seat seat = createSeat(theater, i);
            ShowSeat showSeat = new ShowSeat(showInformation, seat, seatGrade, DEFAULT_FEE);
            showSeats.add(showSeat);
        }
        return showSeats;
    }

    public static Reservation createReservation() {
        ShowSeat showSeat = createShowSeat();
        return new Reservation(showSeat, DEFAULT_EMAIL);
    }

    public static List<Reservation> createReservations(int count) {
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Reservation reservation = createReservation();
            reservations.add(reservation);
        }
        return reservations;
    }

    public static ShowInformation createShowInformation() {
        Theater theater = createTheater(DEFAULT_THEATER_NAME);
        Show show = createShow(DEFAULT_SHOW_TITLE);
        return new ShowInformation(show, theater, DEFAULT_START_TIME);
    }

    public static List<ShowInformation> createShowInformations(int count) {
        List<ShowInformation> showInformations = new ArrayList<>();
        for(int i=0; i<count; i++) {
            ShowInformation showInformation = createShowInformation();
            showInformations.add(showInformation);
        }
        return showInformations;
    }

    public static Seat createSeat() {
        Theater theater = createTheater(DEFAULT_THEATER_NAME);
        return new Seat(theater, DEFAULT_SEAT_POSITION);
    }

    public static List<Seat> createSeats(int count) {
        List<Seat> seats = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Seat seat = createSeat();
            seats.add(seat);
        }
        return seats;
    }
}
