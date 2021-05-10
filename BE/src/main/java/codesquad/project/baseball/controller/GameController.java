package codesquad.project.baseball.controller;

import codesquad.project.baseball.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity(gameService.getGameList(), HttpStatus.OK);
    }

    @GetMapping("/{gameId}/inning/{inningId}")
    public ResponseEntity getPlayPage(@PathVariable Long gameId, @PathVariable Long inningId) {
        return new ResponseEntity(gameService.getNowInningInGame(gameId, inningId), HttpStatus.OK);
    }
}
