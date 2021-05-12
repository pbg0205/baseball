package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.BallCount;
import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Inning;
import codesquad.project.baseball.domain.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Arrays;
import java.util.List;

@JsonPropertyOrder({"inningId", "inning_number", "attack_team_id", "ball_count", "out_count", "pitcher_name",
        "pitcher_number", "batter_name", "batter_number", "basse_status", "score", "home_team_score", "away_team_score"})
public class InningDto {
    private Long inningId;

    @JsonProperty("innning_number")
    private int inningNumber;

    @JsonProperty("attack_team_id")
    private Long teamId;

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
        this.teamId = inning.getTeamId();
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

    @JsonCreator
    public InningDto(Long inningId, int inningNumber, Long teamId, String ballCount, int outCount,
                     String pitcherName, Long pitchNumber, String batterName, Long batterNumber,
                     List<String> baseStatus, int score, int homeTeamScore, int awayTeamScore) {
        this.inningId = inningId;
        this.inningNumber = inningNumber;
        this.teamId = teamId;
        this.ballCount = ballCount;
        this.outCount = outCount;
        this.pitcherName = pitcherName;
        this.pitchNumber = pitchNumber;
        this.batterName = batterName;
        this.batterNumber = batterNumber;
        this.baseStatus = baseStatus;
        this.score = score;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Long getInningId() {
        return inningId;
    }

    public Long getTeamId() {
        return teamId;
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

    public void appendBallCount(char ballCountValue) {
        this.ballCount += ballCountValue;
    }

    public void addOutCount() {
        this.outCount++;
        this.ballCount = "";
    }

    public boolean isAbleToChangeInning() {
        return outCount == 3;
    }

    public void moveBase() {
        List<String> hit1Status = Arrays.asList(new String[]{"1"});
        List<String> hit2Status = Arrays.asList(new String[]{"1","2"});
        List<String> hit3Status = Arrays.asList(new String[]{"1", "2", "3"});

        if(this.baseStatus.equals(hit3Status)) {
            score++;
        }

        if(this.baseStatus.equals(hit2Status)) {
            this.baseStatus = hit3Status;
        }

        if (this.baseStatus.equals(hit1Status)) {
            this.baseStatus = hit2Status;
        }

        if(this.baseStatus.size() == 0) {
            this.baseStatus = hit1Status;
        }

        ballCount = "";
    }

    public boolean hitBall () {
        for (char ball : ballCount.toCharArray()) {
            if(ball == BallCount.HIT.getBallTypeValue()) {
                return true;
            }
        }
        return false;
    }

    public void updateInningDto(Inning inning, Game game, Player nextBatter, Player pitcher) {
        this.inningId = inning.getInningId();
        this.inningNumber = inning.getInningNumber();
        this.teamId = inning.getTeamId();
        this.ballCount = inning.getNowBallCount();
        this.outCount = inning.getNowOutCount();
        this.pitcherName = pitcher.getPlayerName();
        this.pitchNumber = inning.getNowPitcherId();
        this.batterName = nextBatter.getPlayerName();
        this.batterNumber = inning.getNowBatterId();
        this.baseStatus = inning.getNowBaseStatus();
        this.score = inning.getScore();
        this.homeTeamScore = game.getHomeTeamScore();
        this.awayTeamScore = game.getAwayTeamScore();
    }

    public void updateToNextBatter(Player batter) {
        this.batterName = batter.getPlayerName();
        this.batterNumber = batter.getPlayerId();
    }

}
