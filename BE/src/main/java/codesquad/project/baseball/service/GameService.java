package codesquad.project.baseball.service;

import codesquad.project.baseball.controller.GameController;
import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Inning;
import codesquad.project.baseball.domain.Player;
import codesquad.project.baseball.domain.Team;
import codesquad.project.baseball.dto.GameDto;
import codesquad.project.baseball.dto.InningDto;
import codesquad.project.baseball.repository.GameRepository;
import codesquad.project.baseball.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    public GameService(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    public List<GameDto> getGameList() {
        return gameRepository.findAll().stream()
                .map(game -> new GameDto(game,
                        findTeam(game.getHomeTeamId()),
                        findTeam(game.getAwayTeamId())))
                .collect(Collectors.toList());
    }

    public InningDto getNowInningInGame(Long gameId, Long inningId) {
        Game game = gameRepository.findById(gameId).orElseThrow(RuntimeException::new);
        Inning nowInning = findInningFromInnings(game.getInnings(), inningId);

        LOGGER.debug("nowInning : {}", nowInning);

        Player nowBatter = findPlayer(game.getHomeTeamId(), nowInning.getNowBatterId());
        Player nowPitcher = findPlayer(game.getAwayTeamId(), nowInning.getNowPitcherId());

        LOGGER.debug("now batter : {}", nowBatter);
        LOGGER.debug("now pitcher : {}", nowPitcher);

        return new InningDto(nowInning, game, nowBatter, nowPitcher);
    }

    public Inning findInningFromInnings(List<Inning> innings, Long inningId) {
        return innings.stream()
                .filter(inning -> inning.isSameInning(inningId))
                .findFirst().orElseThrow(RuntimeException::new);
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(RuntimeException::new);
    }

    public Player findPlayer(Long teamId, Long playerId) {
        return findTeam(teamId).getPlayer(playerId);
    }
}
