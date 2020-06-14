package bep.lingogame.controller;

import bep.lingogame.service.HighScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/highscore")
public class HighScoreController {
    private final transient HighScoreService highScoreService;

    public HighScoreController(final HighScoreService highScoreService) {
        this.highScoreService = highScoreService;
    }

    @GetMapping
    public List<String> findAll() {
        return highScoreService.findAll();
    }


}
