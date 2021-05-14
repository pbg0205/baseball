package codesquad.project.baseball.controller;

import codesquad.project.baseball.dto.InningDto;
import codesquad.project.baseball.service.GameService;
import codesquad.project.baseball.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baseball")
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private final ScoreService scoreService;

    public GameController(GameService gameService, ScoreService scoreService) {
        this.gameService = gameService;
        this.scoreService = scoreService;
    }

    @GetMapping
    public ResponseEntity getGamePage() {
        LOGGER.debug("GET METHOD : /baseball");
        return new ResponseEntity(gameService.getGameListDto(), HttpStatus.OK);
    }

    @GetMapping("/{gameId}/inning/{inningId}")
    public ResponseEntity getPlayPage(@PathVariable Long gameId, @PathVariable Long inningId) {
        LOGGER.debug("GET METHOD : /baseball/{}/inning/{}", gameId, inningId);
        return new ResponseEntity(gameService.getInningDtoInGame(gameId, inningId), HttpStatus.OK);
    }

    @PutMapping("/{gameId}/inning/{inningId}/pitch")
    public ResponseEntity pitch(@PathVariable Long gameId, @PathVariable Long inningId,
                                @RequestBody InningDto inningDto) {
        return new ResponseEntity(gameService.pitch(gameId, inningId, inningDto), HttpStatus.OK);
    }

    @GetMapping("{gameId}/score/{teamId}")
    public ResponseEntity getScorePage(@PathVariable Long gameId) {
        return new ResponseEntity(scoreService.getBattingStatFromTeamId(gameId), HttpStatus.OK);
    }
}
