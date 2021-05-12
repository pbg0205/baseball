package codesquad.project.baseball.repository;

import codesquad.project.baseball.dao.PlayerStatDao;
import codesquad.project.baseball.domain.BattingStat;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BattingStatusRepository extends CrudRepository<BattingStat, Long> {
    //custom query를 만들자.
    @Query("SELECT PLAYER.PLAYER_ID, PLAYER.PLAYER_NAME," +
            " BATTING_STAT.AT_BAT, BATTING_STAT.HITS, BATTING_STAT.OUT FROM BATTING_STAT " +
            "INNER JOIN PLAYER ON BATTING_STAT.PLAYER_ID = PLAYER.PLAYER_ID " +
            "WHERE BATTING_STAT.TEAM_ID =:teamId and GAME_ID =:gameId")
    List<PlayerStatDao> findAllByTeamIdAndGameId(@Param("gameId")Long gameId, @Param("teamId")Long teamId);
}
