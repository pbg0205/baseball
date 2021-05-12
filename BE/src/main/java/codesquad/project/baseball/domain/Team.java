package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.ArrayList;
import java.util.List;

public class Team {
    @Id
    private Long teamId;

    private String teamName;

    @MappedCollection(idColumn = "team_id")
    private List<Player> players = new ArrayList<>();

    public Team(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getPlayer(Long playerId) {
        return players.stream().filter(player -> player.isSamePlayer(playerId))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
