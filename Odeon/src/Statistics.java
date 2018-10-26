import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    public static void getNumberOfSpectatorsPerFilm(List<RoomScreen> roomScreens) {
        Map<String, Integer> map = new HashMap<>();

        for (RoomScreen roomScreen : roomScreens) {
            String filmName = roomScreen.getFilm().getName();

            int nbrOfBookings = roomScreen.getBookingsCount();

            if (map.containsKey(filmName)) {
                map.put(filmName, map.get(filmName) + nbrOfBookings);
            } else {
                map.put(filmName, nbrOfBookings);
            }
        }

        map.forEach((filmName, spectatorCount) ->
                System.out.println("The film '" + filmName +
                        "' has " + spectatorCount + " spectators."));
    }
}
