package codesquad.project.baseball.service;

import codesquad.project.baseball.controller.GameController;
import codesquad.project.baseball.domain.Game;
import codesquad.project.baseball.domain.Inning;
import codesquad.project.baseball.dto.GameDto;
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
                teamRepository.findById((game.getHomeTeamId())).orElseThrow(RuntimeException::new),
                teamRepository.findById((game.getAwayTeamId())).orElseThrow(RuntimeException::new)))
                .collect(Collectors.toList());
    }

    public Inning getNowInningInGame(Long gameId, Long inningId) {
        Game game = gameRepository.findById(gameId).orElseThrow(RuntimeException::new);
        Inning nowInning = game.getInnings().stream().filter(inning -> inning.isSameInning(inningId))
                .findFirst().orElseThrow(RuntimeException::new);

        return nowInning;
    }
}
