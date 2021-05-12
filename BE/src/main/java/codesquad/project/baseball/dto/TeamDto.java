package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeamDto {
    @JsonIgnore
    private Long teamId;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("home_team_score")
    private List<Integer> scoreList;

    @JsonProperty("players")
    private List<PlayerStatDto> playerStatDtoList;

    public TeamDto(Team team, List<Integer> scoreList, List<PlayerStatDto> playerStatDtoList) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.scoreList = scoreList;
        this.playerStatDtoList = playerStatDtoList;
    }
}
