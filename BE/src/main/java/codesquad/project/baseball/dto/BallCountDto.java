package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.BallCount;

public class BallCountDto {
    private static final int THREE_STRIKE = 3;
    private static final int FOUR_BALL = 4;

    private int strike;
    private int ball;

    public BallCountDto(String ballCount) {
        this.strike = countStrike(ballCount);
        this.ball = countBall(ballCount);
    }

    private int countStrike(String ballCount) {
        int count = 0;
        for (char ballType : ballCount.toCharArray()) {
            if(ballType == BallCount.STRIKE.getBallTypeValue()) {
                count++;
            }
        }
        return count;
    }

    private int countBall(String ballCount) {
        int count = 0;
        for (char ballType : ballCount.toCharArray()) {
            if(ballType == BallCount.BALL.getBallTypeValue()) {
                count++;
            }
        }
        return count;
    }

    public int getStrike() {
        return strike;
    }

    public int getBall() {
        return ball;
    }

    public boolean isOut() {
        return strike == THREE_STRIKE;
    }

    public boolean isFourBall() {
        return ball == FOUR_BALL;
    }

    @Override
    public String toString() {
        return "BallCountDto{" +
                "strike=" + strike +
                ", ball=" + ball +
                '}';
    }
}
