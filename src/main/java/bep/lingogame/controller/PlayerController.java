package bep.lingogame.controller;


import bep.lingogame.domain.Player;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final transient PlayerService playerService;
    private transient Long playerId;

    public PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Long createNew(final @RequestBody Player player) {
        final Player newplayer = playerService.createNew(player);
        if (newplayer != null) {
            playerId = newplayer.id;
            return newplayer.id;
        } else {
            return null;
        }
    }

    public Long returnPlayerId(){

        return playerId;
    }

}