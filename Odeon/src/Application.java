import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class Application {

    private Scanner scanner;

    private List<Film> filmsList;
    private Map<LocalDate, List<RoomScreen>> screenMap;
    private BookingManager bookingManager;

    public Application() {
        scanner = new Scanner(System.in);
        bookingManager = new BookingManager();

        initializeFilms();
        initializeScreens();
        initializeBookings();
        startWork();
    }

    private void initializeFilms() {
        filmsList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Rating> ratings = new ArrayList<>();
            ratings.add(new Rating(1, "Awesome"));
            ratings.add(new Rating(1, "Really Awesome"));
            ratings.add(new Rating(1, "ReallyReally Awesome"));
            ratings.add(new Rating(1, "ReallyReallyReally Awesome"));
            ratings.add(new Rating(1, "ReallyReallyReallyReally Awesome"));

            filmsList.add(new Film("film" + (i + 1), ratings));
        }
    }

    private void initializeBookings() {
        makeBooking(25, 3, 2018,
                "film1", "seat1", SeatStatus.VIP, "night");
        makeBooking(20, 3, 2018,
                "film1", "seat1", SeatStatus.VIP, "night");
        makeBooking(19, 3, 2018,
                "film2", "seat1", SeatStatus.STANDARD, "night");
        makeBooking(10, 3, 2018,
                "film2", "seat10", SeatStatus.VIP, "afternoon");
        makeBooking(12, 3, 2018,
                "film3", "seat10", SeatStatus.VIP, "afternoon");
        makeBooking(11, 3, 2018,
                "film3", "seat11", SeatStatus.STANDARD, "evening");
        makeBooking(9, 3, 2018,
                "film3", "seat12", SeatStatus.VIP, "afternoon");
        makeBooking(9, 3, 2018,
                "film4", "seat1", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat1", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat2", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat3", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat4", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat5", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat6", SeatStatus.STANDARD, "afternoon");
        makeBooking(9, 3, 2018,
                "film5", "seat7", SeatStatus.STANDARD, "afternoon");
    }

    private void startWork() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayOptionsMenu();

            int selectedAction = scanner.nextInt();
            switch (selectedAction) {
                case 1:
                    makeBooking();
                    break;
                case 2:
                    cancelBooking();
                    break;
                case 3:
                    numberOfSpectatorsPerFilm();
                    break;
                case 4:
                    addRating();
                    break;
                case 5:
                    showAverageRating();
                    break;
                case 6:
                    filmWithHighestIncome();
                    break;
            }

            scanner.nextLine();

            System.out.println("Do you want to quit Odeon Booking System (type yes/no)?");
            String quit = scanner.nextLine();

            if (quit.equalsIgnoreCase("yes"))
                break;
        }
    }

    private void numberOfSpectatorsPerFilm() {

        List<RoomScreen> roomScreens = screenMap.values()
                .stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());

        //System.out.println(roomScreens.isEmpty());
        
        //System.out.println(roomScreens);

        Statistics.getNumberOfSpectatorsPerFilm(roomScreens);
    }

    private void addRating() {
        System.out.println("Please choose a film (film1, film2, film3, film4, film5 or film6):");
        String filmName = scanner.nextLine();
        System.out.println("Please specify a rating (type 1,2,3 or 4):");
        int ratingValue = Integer.parseInt(scanner.nextLine());
        System.out.println("Please add a review message:");
        String review = scanner.nextLine();

        Rating rating = new Rating(ratingValue, review);
        filmsList.stream()
                .filter(film -> film.getName().equalsIgnoreCase(filmName))
                .findFirst()
                .get()
                .getRatings()
                .add(rating);

        System.out.println("Review successfully added.");
    }

    private void showAverageRating() {
        Map<String, Double> map = filmsList.stream()
                .collect(Collectors.toMap(Film::getName,
                        f -> f.getRatings().stream()
                                .mapToInt(Rating::getValue)
                                .average().getAsDouble()));

        System.out.println(map);
    }

    private void filmWithHighestIncome() {

        String filmName = screenMap.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(groupingBy(roomScreen -> roomScreen.getFilm().getName(),
                        summingDouble(RoomScreen::getCurrentIncome)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        System.out.println("The film generated the highest income is: " + filmName);
    }

    private void cancelBooking() {
        System.out.println("Please choose a day of a month:");
        int day = scanner.nextInt();
        System.out.println("Please choose a month in numerical format (1,2,3,4...etc (until 6-2018).):");
        int month = scanner.nextInt();
        System.out.println("Please choose a year:");
        int year = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Please choose a film (film1, film2, film3, film4, film5 or film6):");
        String filmName = scanner.nextLine();
        System.out.println("Please choose a seat (seat1, seat2, seat3...etc until seat50):");
        String seatName = scanner.nextLine();
        System.out.println("Please choose a period of time (afternoon, evening, night or late_night):");
        String periodName = scanner.nextLine();

        LocalDate date = LocalDate.of(year, month, day);
        List<RoomScreen> roomScreens = screenMap.get(date);

        for (RoomScreen roomScreen : roomScreens) {
            if (roomScreen.getFilm().getName().equals(filmName)) {
                PeriodScreen periodScreen = roomScreen.getScreenBasedOnPeriod(periodName);
                bookingManager.cancelBooking(seatName, periodScreen);
            }
        }
    }

    private void makeBooking() {
        System.out.println("Please choose a day (booking in the past are not allowed):");
        int day = scanner.nextInt();
        System.out.println("Please choose a month in a numerical format (1,2,3,4...etc, booking is available from today until 1-6-2018):");
        int month = scanner.nextInt();
        System.out.println("Please choose a year:");
        int year = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Please choose a film (film1, film2, film3, film4, film5 or film6):");
        String filmName = scanner.nextLine();
        System.out.println("Please choose a seat (seat1, seat2, seat3...etc until seat50):");
        String seatName = scanner.nextLine();
        System.out.println("Do you want a VIP seat ? (type y/n)");
        char vip = scanner.nextLine().charAt(0);
        System.out.println("Please choose a period of time (afternoon, evening, night or late_night):");
        String periodName = scanner.nextLine();

        makeBooking(day, month, year, filmName, seatName,
                vip == 'y' ? SeatStatus.VIP : SeatStatus.STANDARD,
                periodName);
    }

    private void makeBooking(int day, int month, int year,
                             String filmName, String seatName, SeatStatus isVIP, String periodName) {
        LocalDate date = LocalDate.of(year, month, day);
        List<RoomScreen> roomScreens = screenMap.get(date);

        for (RoomScreen roomScreen : roomScreens) {
            if (roomScreen.getFilm().getName().equals(filmName)) {
                PeriodScreen periodScreen = roomScreen.getScreenBasedOnPeriod(periodName);
                if (periodScreen != null)
                    bookingManager.bookSeat(seatName, isVIP, periodScreen);
                else
                    System.out.println("Invalid period requested.");

                break;
            }
        }
    }

    private void displayOptionsMenu() {
        System.out.println("Welcome to Odeon Booking System. What would you like to do (press 1,2,3,4,5 or 6 following ENTER command)?");
        System.out.println("1. Book a seat");
        System.out.println("2. Cancel a booking");
        System.out.println("3. Show the number of spectators per film");
        System.out.println("4. Add a rating");
        System.out.println("5. Show average ratings");
        System.out.println("6. Show the film generated the highest income");
    }

    private void initializeScreensForOneDay(LocalDate date) {
        RoomScreen greenScreen = new RoomScreen(date, "green", filmsList.get(0));
        RoomScreen redScreen = new RoomScreen(date, "red", filmsList.get(1));
        RoomScreen blueScreen = new RoomScreen(date, "blue", filmsList.get(2));
        RoomScreen blackScreen = new RoomScreen(date, "black", filmsList.get(3));
        RoomScreen whiteScreen = new RoomScreen(date, "white", filmsList.get(4));
        RoomScreen yellowScreen = new RoomScreen(date, "yellow", filmsList.get(5));

        List<RoomScreen> roomScreens = Arrays.asList(
                greenScreen, redScreen, blueScreen, blackScreen,
                yellowScreen, whiteScreen);

        screenMap.put(date, roomScreens);
    }

    private void initializeScreens() {
        screenMap = new HashMap<>();
        LocalDate date = LocalDate.now();

        for (int i = 0; i < 170; i++) {
            initializeScreensForOneDay(date);
            date = date.plusDays(1);
        }
    }
}
