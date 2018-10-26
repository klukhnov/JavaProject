public class Rating {
    private int value;
    private String review;

    public Rating(int value, String review) {
        this.value = value;
        this.review = review;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "value=" + value +
                ", review='" + review + '\'' +
                '}';
    }
}
