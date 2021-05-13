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

@JsonPropertyOrder({"inning_id", "game_id", "inning_number", "attack_team_id", "ball_count", "out_count", "pitcher_name",
        "pitcher_number", "batter_name", "batter_number", "basse_status", "score", "home_team_score", "away_team_score"})
public class InningDto {
    @JsonProperty("inning_id")
    private Long inningId;

    @JsonProperty("game_id")
    private Long gameId;

    @JsonProperty("innning_number")
    private int inningNumber;

    @JsonProperty("attack_team_id")
    private Long teamId;

    @JsonProperty("ball_count")
    private String nowBallCount;

    @JsonProperty("out_count")
    private int nowOutCount;

    @JsonProperty("pitcher_name")
    private String pitcherName;

    @JsonProperty("pitcher_number")
    private Long pitchId;

    @JsonProperty("batter_name")
    private String batterName;

    @JsonProperty("batter_number")
    private Long batterId;

    @JsonProperty("batter_status")
    private List<String> baseStatus;

    private int score;

    @JsonProperty("home_team_score")
    private int homeTeamScore;

    @JsonProperty("away_team_score")
    private int awayTeamScore;

    public InningDto(Inning inning, Game game, Player nowBatter, Player nowPitcher) {
        this.inningId = inning.getInningId();
        this.gameId = inning.getGameId();
        this.inningNumber = inning.getInningNumber();
        this.teamId = inning.getTeamId();
        this.nowBallCount = inning.getNowBallCount();
        this.nowOutCount = inning.getNowOutCount();
        this.pitcherName = nowBatter.getPlayerName();
        this.pitchId = inning.getNowPitcherId();
        this.batterName = nowPitcher.getPlayerName();
        this.batterId = inning.getNowBatterId();
        this.baseStatus = parseBaseStatus(inning.getNowBaseStatus());
        this.score = inning.getScore();
        this.homeTeamScore = game.getHomeTeamScore();
        this.awayTeamScore = game.getAwayTeamScore();
    }

    @JsonCreator
    public InningDto(Long inningId, Long gameId, int inningNumber, Long teamId, String nowBallCount, int nowOutCount,
                     String pitcherName, Long pitchId, String batterName, Long batterId,
                     List<String> baseStatus, int score, int homeTeamScore, int awayTeamScore) {
        this.inningId = inningId;
        this.gameId = gameId;
        this.inningNumber = inningNumber;
        this.teamId = teamId;
        this.nowBallCount = nowBallCount;
        this.nowOutCount = nowOutCount;
        this.pitcherName = pitcherName;
        this.pitchId = pitchId;
        this.batterName = batterName;
        this.batterId = batterId;
        this.baseStatus = baseStatus;
        this.score = score;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Long getInningId() {
        return inningId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public String getNowBallCount() {
        return nowBallCount;
    }

    public int getNowOutCount() {
        return nowOutCount;
    }

    public Long getPitchId() {
        return pitchId;
    }

    public Long getBatterId() {
        return batterId;
    }

    public List<String> getBaseStatus() {
        return baseStatus;
    }

    public int getScore() {
        return score;
    }

    private List<String> parseBaseStatus(String nowBaseStatus) {
        return Arrays.asList(nowBaseStatus.split(""));
    }

    public void appendBallCount(char ballCountValue) {
        this.nowBallCount += ballCountValue;
    }

    public void addOutCount() {
        this.nowOutCount++;
        this.nowBallCount = "";
    }

    public boolean isThreeOut() {
        return nowOutCount == 3;
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

        nowBallCount = "";
    }

    public boolean hitBall () {
        for (char ball : nowBallCount.toCharArray()) {
            if(ball == BallCount.HIT.getBallTypeValue()) {
                return true;
            }
        }
        return false;
    }

    public void updateNextInningAndBatter(Inning inning, Player nextBatter) {
        updateToNextInning(inning);
        updateToNextBatter(nextBatter);
    }

    public void updateToNextInning(Inning nextInning) {
        this.inningId = nextInning.getInningId();
        this.inningNumber = nextInning.getInningNumber();
    }

    public void updateToNextBatter(Player batter) {
        this.batterName = batter.getPlayerName();
        this.batterId = batter.getPlayerId();
    }

    @Override
    public String toString() {
        return "InningDto{" +
                "inningId=" + inningId +
                ", gameId=" + gameId +
                ", inningNumber=" + inningNumber +
                ", teamId=" + teamId +
                ", nowBallCount='" + nowBallCount + '\'' +
                ", nowOutCount=" + nowOutCount +
                ", pitcherName='" + pitcherName + '\'' +
                ", pitchId=" + pitchId +
                ", batterName='" + batterName + '\'' +
                ", batterId=" + batterId +
                ", baseStatus=" + baseStatus +
                ", score=" + score +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                '}';
    }
}
