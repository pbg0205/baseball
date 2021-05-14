package codesquad.project.baseball.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStatDao {
    @JsonIgnore
    private Long playerId;

    @JsonProperty("player_id")
    private String playerName;

    @JsonProperty("at_bat")
    private int atBat;

    private int hits;

    private int out;

    public PlayerStatDao(Long playerId, String playerName, int atBat, int hits, int out) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.atBat = atBat;
        this.hits = hits;
        this.out = out;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
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
}
