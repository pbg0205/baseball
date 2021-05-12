package codesquad.project.baseball.utils;

import java.util.Random;

public class RandomUtils {
    private static Random random = new Random();

    private RandomUtils() {
    }

    public static int generateBallCountNumber() {
        return random.nextInt(3) + 1;
    }
}
