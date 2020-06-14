package bep.lingogame.controller;

import bep.lingogame.Game;
import bep.lingogame.Player;
import bep.lingogame.service.GameService;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final transient GameService gameService;
    private final transient PlayerService playerService;
    private final transient PlayerController playerController;

    public GameController(final GameService gameService, final PlayerService playerService, final PlayerController playerController) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.playerController = playerController;
    }

    @PostMapping()
    public Game createNew() {
        final Player player = playerService.findById(playerController.returnPlayerId());
        final Game newgame = gameService.createNew(player);
        if (newgame != null) {
            return newgame;
        } else {
            return null;
        }
    }

}
