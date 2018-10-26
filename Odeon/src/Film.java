import java.util.ArrayList;
import java.util.List;

public class Film {
    private String name;
    private List<Rating> ratings;

    public Film(String name) {
        this.name = name;
        ratings = new ArrayList<>();
    }

    public Film(String name, List<Rating> ratings) {
        this.name = name;
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
