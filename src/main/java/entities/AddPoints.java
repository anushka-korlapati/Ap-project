package entities;

public interface AddPoints {
    int score = 0;

    public default void plusOne(int score) {
        score += 1;
    }

    public default void plusTwo(int score) {
        score += 2;
    }

    public default void plusFive(int score) {
        score += 5;
    }
}