package codesquad.project.baseball.dto;

import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Team;

public class GameDto {
    private Long gameId;

    private Long homeTeamId;
    private String homeTeamName;
    private int homeTeamPoint;

    private Long awayTeamId;
    private String awayTeamName;
    private int awayTeamPoint;

    public GameDto(Game game, Team homeTeam, Team awayTeam) {
        this.gameId = game.getGameId();
        this.homeTeamId = homeTeam.getTeamId();
        this.homeTeamName = homeTeam.getTeamName();
        this.homeTeamPoint = game.getHomeTeamScore();
        this.awayTeamId = awayTeam.getTeamId();
        this.awayTeamName = awayTeam.getTeamName();
        this.awayTeamPoint = game.getAwayTeamScore();
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public int getHomeTeamPoint() {
        return homeTeamPoint;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public int getAwayTeamPoint() {
        return awayTeamPoint;
    }
}
