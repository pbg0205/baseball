package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;

public class Game {
    @Id
    private Long gameId;

    private Long homeTeamId;
    private int homeTeamScore;

    private Long awayTeamId;
    private int awayTeamScore;

    public Game(Long homeTeamId, int homeTeamScore, Long awayTeamId, int awayTeamScore) {
        this.homeTeamId = homeTeamId;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamId = awayTeamId;
        this.awayTeamScore = awayTeamScore;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }
}
