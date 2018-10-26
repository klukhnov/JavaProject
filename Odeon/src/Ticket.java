public class Ticket {
    private static final int DEFAULT_TICKET_PRICE = 5;

    private double price;
    private Period period;
    private SeatStatus seatStatus;

    public Ticket(SeatStatus seatStatus, Period period) {
        price = DEFAULT_TICKET_PRICE;
        this.period = period;
        this.seatStatus = seatStatus;
    }

    public Ticket(SeatStatus seatStatus, double price, Period period) {
        this.seatStatus = seatStatus;
        this.price = price;
        this.period = period;
    }

    public double getPrice() {
        double finalPrice;

        if (seatStatus == SeatStatus.STANDARD)
            finalPrice = price;
        else {
            finalPrice = price * 2;
        }

        if (period.getName() == TimeOfDay.NIGHT)
            finalPrice += price / 5;

        return finalPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price=" + price +
                ", period=" + period +
                ", seatStatus=" + seatStatus +
                '}';
    }
}
