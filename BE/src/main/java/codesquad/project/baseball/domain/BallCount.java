package codesquad.project.baseball.domain;

import java.util.Arrays;

public enum BallCount {
    STRIKE(1, 'S'),
    BALL(2, 'B'),
    HIT(3, 'H');

    private int ballCountNumber;

    private char ballTypeValue;

    BallCount(int ballCountNumber, char ballTypeValue) {
        this.ballCountNumber = ballCountNumber;
        this.ballTypeValue = ballTypeValue;
    }

    public int getBallCountNumber() {
        return ballCountNumber;
    }

    public char getBallTypeValue() {
        return ballTypeValue;
    }

    public static char findBallTypeValue(int ballCountNumber) {
        return Arrays.stream(values()).filter(ballCount -> ballCount.ballCountNumber == ballCountNumber)
                .findFirst().orElseThrow(RuntimeException::new).ballTypeValue;
    }
}
