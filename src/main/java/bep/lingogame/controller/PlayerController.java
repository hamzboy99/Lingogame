package bep.lingogame.controller;


import bep.lingogame.domain.Player;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public String createNew(@RequestBody Player player) {
        try {
            playerService.createNew(player);

        } catch (ResponseStatusException exc) {
            return exc.toString();
        }
        return "Post succesful";
    }

}