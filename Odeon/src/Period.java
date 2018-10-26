public class Period {
    private TimeOfDay name;

    public Period(TimeOfDay name) {
        this.name = name;
    }

    public Period(String period) {
        switch (period) {
            case "afternoon":
                name = TimeOfDay.AFTERNOON;
                break;
            case "evening":
                name = TimeOfDay.EVENING;
                break;
            case "night":
                name = TimeOfDay.NIGHT;
                break;
            case "late_night":
                name = TimeOfDay.LATE_NIGHT;
                break;
        }
    }

    public TimeOfDay getName() {
        return name;
    }
}
