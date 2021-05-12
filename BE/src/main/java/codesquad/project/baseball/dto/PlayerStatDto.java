package codesquad.project.baseball.dto;

import codesquad.project.baseball.dao.PlayerStatDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"player_id", "at_bat", "hits", "out", "average"})
public class PlayerStatDto {
    @JsonIgnore
    private Long playerId;

    @JsonProperty("player_id")
    private String playerName;

    @JsonProperty("at_bat")
    private int atBat;

    private int hits;

    private int out;

    public PlayerStatDto(PlayerStatDao playerStatDao) {
        this.playerId = playerStatDao.getPlayerId();
        this.playerName = playerStatDao.getPlayerName();
        this.atBat = playerStatDao.getAtBat();
        this.hits = playerStatDao.getHits();
        this.out = playerStatDao.getOut();
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

    @Override
    public String toString() {
        return "PlayerStatDto{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", atBat=" + atBat +
                ", hits=" + hits +
                ", out=" + out +
                '}';
    }
}
