package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.ArrayList;
import java.util.List;

public class Game {
    @Id
    private Long gameId;

    private Long homeTeamId;
    private int homeTeamScore;

    private Long awayTeamId;
    private int awayTeamScore;

    @MappedCollection(idColumn = "game_id")
    private List<Inning> innings = new ArrayList<>();

    private Long nowInningId;

    public Game(Long homeTeamId, int homeTeamScore, Long awayTeamId, int awayTeamScore, Long nowInningId) {
        this.homeTeamId = homeTeamId;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamId = awayTeamId;
        this.awayTeamScore = awayTeamScore;
        this.nowInningId = nowInningId;
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

    public Long getNowInningId() {
        return nowInningId;
    }

    public List<Inning> getInnings() {
        return innings;
    }

    public void addInning(Inning inning) {
        innings.add(inning);
    }
}
