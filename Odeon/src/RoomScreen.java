import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

public class RoomScreen {
    private PeriodScreen afternoonPeriodScreen;
    private PeriodScreen nightPeriodScreen;
    private PeriodScreen lateNightPeriodScreen;
    private PeriodScreen eveningPeriodScreen;

    private Film film;

    public RoomScreen(LocalDate date, String color, Film film) {
        afternoonPeriodScreen = new PeriodScreen(date, color, new Period(TimeOfDay.AFTERNOON), film);
        nightPeriodScreen = new PeriodScreen(date, color, new Period(TimeOfDay.NIGHT), film);
        lateNightPeriodScreen = new PeriodScreen(date, color, new Period(TimeOfDay.LATE_NIGHT), film);
        eveningPeriodScreen = new PeriodScreen(date, color, new Period(TimeOfDay.EVENING), film);

        this.film = film;
    }

    public PeriodScreen getScreenBasedOnPeriod(String periodName) {
        if (periodName.equalsIgnoreCase("AFTERNOON"))
            return afternoonPeriodScreen;
        else if (periodName.equalsIgnoreCase("EVENING"))
            return eveningPeriodScreen;
        else if (periodName.equalsIgnoreCase("NIGHT"))
            return nightPeriodScreen;
        else if (periodName.equalsIgnoreCase("LATE_NIGHT"))
            return lateNightPeriodScreen;
        else
            return null;
    }

    public double getCurrentIncome() {
        return Stream.of(afternoonPeriodScreen, eveningPeriodScreen,
                nightPeriodScreen, lateNightPeriodScreen)
                .mapToDouble(PeriodScreen::getCurrentIncome)
                .sum();
    }

    public int getBookingsCount() {

        // stream of 4 period screens
        // after executing map --> stream of 4 seat arrays
        // after executing flatMap --> stream of 200 seats
        // after executing filter --> remove all the seats that dont satisfy the specified condition
        // count returns a long value

        return (int) Stream.of(afternoonPeriodScreen, eveningPeriodScreen,
                nightPeriodScreen, lateNightPeriodScreen)
                .map(PeriodScreen::getSeats)
                .flatMap(Arrays::stream)
                .filter(seat -> seat.isBooked())
                .count();
    }

    public PeriodScreen getAfternoonPeriodScreen() {
        return afternoonPeriodScreen;
    }

    public PeriodScreen getNightPeriodScreen() {
        return nightPeriodScreen;
    }

    public PeriodScreen getLateNightPeriodScreen() {
        return lateNightPeriodScreen;
    }

    public PeriodScreen getEveningPeriodScreen() {
        return eveningPeriodScreen;
    }

    public Film getFilm() {
        return film;
    }

    @Override
    public String toString() {
        return "RoomScreen{" +
                "afternoonPeriodScreen=" + afternoonPeriodScreen +
                ", nightPeriodScreen=" + nightPeriodScreen +
                ", lateNightPeriodScreen=" + lateNightPeriodScreen +
                ", eveningPeriodScreen=" + eveningPeriodScreen +
                ", film=" + film +
                '}';
    }
}
