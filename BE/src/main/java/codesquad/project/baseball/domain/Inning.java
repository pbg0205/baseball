package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inning {
    @Id
    private Long inningId;

    private Long gameId;

    private Long nowBatterId;

    private Long nowPitcherId;

    private int inningNumber;

    private int score;

    private String nowBallCount;

    private int nowOutCount;

    private List<String> nowBaseStatus = new ArrayList<>();

    public Inning(Long gameId, Long nowBatterId, Long nowPitcherId, int inningNumber, int score,
                  String nowBallCount, int nowOutCount, String nowBaseStatus) {
        this.gameId = gameId;
        this.nowBatterId = nowBatterId;
        this.nowPitcherId = nowPitcherId;
        this.inningNumber = inningNumber;
        this.score = score;
        this.nowBallCount = nowBallCount;
        this.nowOutCount = nowOutCount;
        this.nowBaseStatus = parseBaseStatus(nowBaseStatus);
    }

    public Long getInningId() {
        return inningId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getNowBatterId() {
        return nowBatterId;
    }

    public Long getNowPitcherId() {
        return nowPitcherId;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public int getScore() {
        return score;
    }

    public String getNowBallCount() {
        return nowBallCount;
    }

    public int getNowOutCount() {
        return nowOutCount;
    }

    public List<String> getNowBaseStatus() {
        return nowBaseStatus;
    }

    private List<String> parseBaseStatus(String nowBaseStatus) {
        return Arrays.asList(nowBaseStatus.split(""));
    }

    public boolean isSameInning(Long inningId) {
        return this.inningId == inningId;
    }
}
