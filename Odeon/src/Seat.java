public class Seat {
    private String identifier;
    private SeatStatus status;
    private Ticket ticket;
    private boolean isBooked;
    private Period period;

    public Seat(String identifier, SeatStatus status, Period period) {
        this.identifier = identifier;
        this.status = status;
        this.period = period;
        this.ticket = new Ticket(status, period);

        isBooked = false;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "identifier='" + identifier + '\'' +
                ", status=" + status +
                ", ticket=" + ticket +
                ", isBooked=" + isBooked +
                '}';
    }
}
