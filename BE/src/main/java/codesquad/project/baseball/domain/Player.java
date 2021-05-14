package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("PLAYER")
public class Player {
    @Id
    private Long playerId;

    private Long teamId;

    private String playerName;

    private boolean isPitcher;

    public Player(Long playerId, Long teamId, String playerName, boolean isPitcher) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.playerName = playerName;
        this.isPitcher = isPitcher;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isPitcher() {
        return isPitcher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return playerId.equals(player.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playedId=" + playerId +
                ", teamId=" + teamId +
                ", PlayerName='" + playerName + '\'' +
                ", isPitcher=" + isPitcher +
                '}';
    }

    public boolean isSamePlayer(Long playerId) {
        return this.playerId == playerId;
    }
}
