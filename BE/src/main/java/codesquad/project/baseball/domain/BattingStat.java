package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("BATTING_STAT")
public class BattingStat {
    @Id
    private Long battingStatId;

    private Long gameId;

    private Long teamId;

    private Long playerId;

    private int atBat;

    private int hits;

    private int out;

    private double averageHitRatio;

    public BattingStat(Long battingStatId, Long gameId, Long teamId, Long playerId,
                       int atBat, int hits, int out, double averageHitRatio) {
        this.battingStatId = battingStatId;
        this.gameId = gameId;
        this.teamId = teamId;
        this.playerId = playerId;
        this.atBat = atBat;
        this.hits = hits;
        this.out = out;
        this.averageHitRatio = averageHitRatio;
    }

    public Long getBattingStatId() {
        return battingStatId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public int getAtBat() {
        return atBat;
    }

    public int getHits() {
        return hits;
    }

    public int getOut() {
        return out;
    }

    public double getAverageHitRatio() {
        return averageHitRatio;
    }
}
