package codesquad.project.baseball.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BattingStatusDto {
    @JsonProperty("game_id")
    private Long gameId;

    @JsonProperty("home_team")
    private TeamDto homeTeamDto;

    @JsonProperty("away_team")
    private TeamDto awayTeamDto;

    public BattingStatusDto(Long gameId, TeamDto homeTeamDto, TeamDto awayTeamDto) {
        this.gameId = gameId;
        this.homeTeamDto = homeTeamDto;
        this.awayTeamDto = awayTeamDto;
    }
}
