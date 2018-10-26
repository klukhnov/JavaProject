import java.util.Arrays;
import java.util.Random;

public class BookingManager {

    private Random random;

    public BookingManager() {
        random = new Random();
    }

    public void bookSeat(String seatID, SeatStatus seatStatus, PeriodScreen screen) {
        boolean seatFound = false;

        Seat[] seats = screen.getSeats();
        for (Seat seat : seats) {
            if (seat.getIdentifier().equals(seatID)) {
                seatFound = true;
                if (seat.isBooked()) {
                    System.out.println(seatID + " is already booked.");
                } else {
                    if (seatStatus == SeatStatus.VIP) {
                        // User requested a VIP seat
                        Seat[] vipSeats = screen.getVipSeats();
                        if (Arrays.stream(vipSeats).allMatch(Seat::isBooked))
                            System.out.println("There are no remaining seats to book.");
                        else {
                            Seat[] availableVipSeats = Arrays.stream(vipSeats)
                                    .filter(s -> !s.isBooked())
                                    .toArray(Seat[]::new);
                            int generated = random.nextInt(availableVipSeats.length);
                            availableVipSeats[generated].setBooked(true);
                            System.out.println("Random VIP seat booked !");
                        }
                    } else {
                        // User requested a standard seat
                        seat.setBooked(true);
                    }

                    System.out.println(seatID + " successfully booked.");
                }

                break;
            }
        }

        if (!seatFound)
            System.out.println("Invalid seat identifier !");
    }

    public void cancelBooking(String seatID, PeriodScreen screen) {
        boolean seatFound = false;

        Seat[] seats = screen.getSeats();
        for (Seat seat : seats) {
            if (seat.getIdentifier().equals(seatID)) {
                seatFound = true;
                if (seat.isBooked()) {
                    seat.setBooked(false);
                    System.out.println(seatID + "'s booking was successfully cancelled.");
                } else
                    System.out.println(seatID + " is already clear.");

                break;
            }
        }

        if (!seatFound)
            System.out.println("Invalid seat identifier !");
    }
}
