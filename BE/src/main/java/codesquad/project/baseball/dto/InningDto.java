package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Inning;
import codesquad.project.baseball.domain.Player;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InningDto {
    private Long inningId;

    @JsonProperty("innning_number")
    private int inningNumber;

    @JsonProperty("ball_count")
    private String ballCount;

    private int outCount;

    private String pitcherName;

    private Long pitchNumber;

    private String batterName;

    private Long batterNumber;

    private List<String> baseStatus;

    private int score;

    private int homeTeamScore;
    private int awayTeamScore;

    public InningDto(Inning inning, Game game, Player nowBatter, Player nowPitcher) {
        this.inningId = inning.getInningId();
        this.inningNumber = inning.getInningNumber();
        this.ballCount = inning.getNowBallCount();
        this.outCount = inning.getNowOutCount();
        this.pitcherName = nowBatter.getPlayerName();
        this.pitchNumber = inning.getNowPitcherId();
        this.batterName = nowPitcher.getPlayerName();
        this.batterNumber = inning.getNowBatterId();
        this.baseStatus = inning.getNowBaseStatus();
        this.score = inning.getScore();
        this.homeTeamScore = game.getHomeTeamScore();
        this.awayTeamScore = game.getAwayTeamScore();
    }

    public Long getInningId() {
        return inningId;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public String getBallCount() {
        return ballCount;
    }

    public int getOutCount() {
        return outCount;
    }

    public String getPitcherName() {
        return pitcherName;
    }

    public Long getPitchNumber() {
        return pitchNumber;
    }

    public String getBatterName() {
        return batterName;
    }

    public Long getBatterNumber() {
        return batterNumber;
    }

    public List<String> getBaseStatus() {
        return baseStatus;
    }

    public int getScore() {
        return score;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }
}
