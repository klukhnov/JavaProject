public enum TimeOfDay {
    AFTERNOON("2PM"),
    EVENING("6PM"),
    NIGHT("9PM"),
    LATE_NIGHT("11PM");

    private String timing;

    TimeOfDay(String timing) {
        this.timing = timing;
    }

    public String getTiming() {
        return timing;
    }
}
