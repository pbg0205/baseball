package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Inning;
import codesquad.project.baseball.domain.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"inningId", "inning_number", "ball_count", "out_count","pitcher_name", "pitcher_number",
        "batter_name", "batter_number", "basse_status", "score", "home_team_score", "away_team_score"})
public class InningDto {
    private Long inningId;

    @JsonProperty("innning_number")
    private int inningNumber;

    @JsonProperty("ball_count")
    private String ballCount;

    @JsonProperty("out_count")
    private int outCount;

    @JsonProperty("pitcher_name")
    private String pitcherName;

    @JsonProperty("pitcher_number")
    private Long pitchNumber;

    @JsonProperty("batter_name")
    private String batterName;

    @JsonProperty("batter_number")
    private Long batterNumber;

    @JsonProperty("batter_status")
    private List<String> baseStatus;

    private int score;

    @JsonProperty("home_team_score")
    private int homeTeamScore;

    @JsonProperty("away_team_score")
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
