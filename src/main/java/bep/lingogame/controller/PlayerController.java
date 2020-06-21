package bep.lingogame.controller;


import bep.lingogame.domain.Game;
import bep.lingogame.domain.Player;
import bep.lingogame.service.GameService;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final transient PlayerService playerService;
    private final transient GameService gameService;
    private transient Long playerId;
    private transient Long gameId;

    public PlayerController(final PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @GetMapping
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Long createNew(final @RequestBody Player player) {
        final Player newplayer = playerService.createNew(player);
        final Game newGame = gameService.createNew(newplayer);
        if (newplayer != null) {
            playerId = newplayer.id;
            gameId = newGame.id;
            return newplayer.id;
        } else {
            return null;
        }
    }

    public Long returnPlayerId(){
        return playerId;
    }

    public Long returnGameId(){
        return gameId;
    }

}