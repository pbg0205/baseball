package codesquad.project.baseball.service;

import codesquad.project.baseball.dao.PlayerStatDao;
import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Team;
import codesquad.project.baseball.dto.BattingStatusDto;
import codesquad.project.baseball.dto.PlayerStatDto;
import codesquad.project.baseball.dto.TeamDto;
import codesquad.project.baseball.repository.BattingStatusRepository;
import codesquad.project.baseball.repository.GameRepository;
import codesquad.project.baseball.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final BattingStatusRepository battingStatusRepository;

    public ScoreService(GameRepository gameRepository, TeamRepository teamRepository, BattingStatusRepository battingStatusRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.battingStatusRepository = battingStatusRepository;
    }

    public BattingStatusDto getBattingStatFromTeamId(Long gameId, Long teamId) {
        Game game = gameRepository.findById(gameId).orElseThrow(RuntimeException::new);
        TeamDto homeTeamDto = getTeamDto(game, game.getHomeTeamId());
        TeamDto awayTeamDto = getTeamDto(game, game.getAwayTeamId());

        LOGGER.debug("homeTeamDto: {}", homeTeamDto);
        LOGGER.debug("awayTeamDto: {}", awayTeamDto);

        return new BattingStatusDto(gameId, homeTeamDto, awayTeamDto);
    }

    private TeamDto getTeamDto(Game game, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(RuntimeException::new);
        List<PlayerStatDto> teamList = getPlayerStatDtoList(game, teamId);
        List<Integer> scoreList = getScoreList(game, teamId);

        return new TeamDto(team, scoreList, teamList);
    }

    private List<Integer> getScoreList(Game game, Long teamId) {
        return game.getInnings().stream()
                .filter(inning -> inning.getTeamId() == teamId)
                .map(classifiedInning -> classifiedInning.getScore())
                .collect(Collectors.toList());
    }

    private List<PlayerStatDto> getPlayerStatDtoList(Game game, Long teamId) {
        List<PlayerStatDao> playerStatDaoList = battingStatusRepository.
                findAllByTeamIdAndGameId(game.getGameId(), teamId);
        return playerStatDaoList.stream().
                map(playerStatDao -> new PlayerStatDto(playerStatDao)).collect(Collectors.toList());
    }
}
