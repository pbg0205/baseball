package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.BallCount;

import java.util.List;

public class BallCountDto {
    private static final int THREE_STRIKE = 3;
    private static final int FOUR_BALL = 4;

    private int strike;
    private int ball;

    public BallCountDto(List<String> ballCount) {
        this.strike = countBallType(ballCount, BallCount.STRIKE.getBallTypeValue());
        this.ball = countBallType(ballCount, BallCount.BALL.getBallTypeValue());
    }

    private int countBallType(List<String> ballCount, String ballType) {
        int count = 0;
        for (String ball : ballCount) {
            if(ball.equals(ballType)) {
                count++;
            }
        }
        return count;
    }

    private int countBall(List<String> ballCount) {
        int count = 0;
        for (String ballType : ballCount) {
            if(ballType.equals(BallCount.BALL.getBallTypeValue())) {
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
