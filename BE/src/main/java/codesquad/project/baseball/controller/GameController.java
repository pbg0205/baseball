package codesquad.project.baseball.controller;

import codesquad.project.baseball.dto.InningDto;
import codesquad.project.baseball.service.GameService;
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

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity getGamePage() {
        LOGGER.debug("GET METHOD : /baseball");
        return new ResponseEntity(gameService.getGameList(), HttpStatus.OK);
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
    public ResponseEntity getScorePage(@PathVariable Long gameId, @PathVariable Long teamId) {
        return new ResponseEntity("", HttpStatus.OK);
    }
}
