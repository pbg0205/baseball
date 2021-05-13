package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table("GAME")
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

    public void addScore(Long teamId) {
        if(teamId == homeTeamId) {
            this.homeTeamScore++;
        }

        if(teamId == awayTeamId) {
            this.awayTeamScore++;
        }
    }

    public void addInning(Inning inning) {
        innings.add(inning);
    }

    public Inning getInningByInningId(Long inningId) {
        return innings.stream().filter(inning -> inning.getInningId() == inningId)
                .findFirst().orElseThrow(RuntimeException::new);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", homeTeamId=" + homeTeamId +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamId=" + awayTeamId +
                ", awayTeamScore=" + awayTeamScore +
                ", innings=" + innings +
                ", nowInningId=" + nowInningId +
                '}';
    }
}
