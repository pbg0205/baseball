package codesquad.project.baseball.domain;

import java.util.Arrays;

public enum BallCount {
    STRIKE(1, "S"),
    BALL(2, "B"),
    HIT(3, "H");

    private int ballCountNumber;

    private String ballTypeValue;

    BallCount(int ballCountNumber, String ballTypeValue) {
        this.ballCountNumber = ballCountNumber;
        this.ballTypeValue = ballTypeValue;
    }

    public int getBallCountNumber() {
        return ballCountNumber;
    }

    public String getBallTypeValue() {
        return ballTypeValue;
    }

    public static String findBallTypeValue(int ballCountNumber) {
        return Arrays.stream(values()).filter(ballCount -> ballCount.ballCountNumber == ballCountNumber)
                .findFirst().orElseThrow(RuntimeException::new).ballTypeValue;
    }
}
