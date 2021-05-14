package codesquad.project.baseball.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GameListDto {
    @JsonProperty("games")
    private List<GameDto> gameDtoList;

    public GameListDto(List<GameDto> gameDtoList) {
        this.gameDtoList = gameDtoList;
    }
}
