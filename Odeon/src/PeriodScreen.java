import java.time.LocalDate;
import java.util.Arrays;


public class PeriodScreen {
    private static final int NUMBER_OF_SEATS_PER_SCREEN = 50;
    private static final int NUMBER_OF_VIP_SEATS = 10;

    private Seat[] seats;
    private LocalDate localDate;
    private String color;
    private Period period;
    private Film film;

    public PeriodScreen(LocalDate localDate, String color, Period period, Film film) {
        seats = new Seat[NUMBER_OF_SEATS_PER_SCREEN];
        this.localDate = localDate;
        this.period = period;
        this.color = color;
        this.film = film;

        initializeSeats(NUMBER_OF_VIP_SEATS,
                NUMBER_OF_SEATS_PER_SCREEN - NUMBER_OF_VIP_SEATS);
    }

    private void initializeSeats(int nbrOfVipSeats, int nbrOfStandardSeats) {
        int counter = 0;

        for (int i = 0; i < nbrOfStandardSeats; i++, counter++) {
            Seat standardSeat = createStandardSeat("seat" + (counter + 1));
            seats[counter] = standardSeat;
        }

        for (int i = 0; i < nbrOfVipSeats; i++, counter++) {
            Seat vipSeat = createVipSeat("seat" + (counter + 1));
            seats[counter] = vipSeat;
        }
    }

    private Seat createStandardSeat(String identifier) {
        return new Seat(identifier, SeatStatus.STANDARD, period);
    }

    private Seat createVipSeat(String identifier) {
        return new Seat(identifier, SeatStatus.VIP, period);
    }

    public Seat[] getSeats() {
        return seats;
    }

    public Seat[] getVipSeats() {
        return Arrays.stream(seats)
                .filter(seat -> seat.getStatus() == SeatStatus.VIP)
                .toArray(Seat[]::new);
    }

    public double getCurrentIncome() {
        return Arrays.stream(seats)
                .filter(Seat::isBooked)
                .map(Seat::getTicket)
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    @Override
    public String toString() {
        return "PeriodScreen{" +
                "seats=" + Arrays.toString(seats) +
                ", localDate=" + localDate +
                ", color='" + color + '\'' +
                ", period=" + period +
                ", film=" + film +
                '}';
    }
}
