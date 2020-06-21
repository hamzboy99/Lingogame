package bep.lingogame.service;

import bep.lingogame.domain.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HighScoreService {
    private final transient PlayerService playerService;

    public HighScoreService(final PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<String> findAll() {
        final List<Player> player = playerService.findAll();
        final List<String> players = new ArrayList<>();

        for (final Player data : player) {
            final String name = data.name;
            final int score = data.score;
            players.add("Score:"+ score + " Name:" + name);

        }
        players.sort(Collections.reverseOrder());
        players.add(0, "HighScore:"); //Add highscore title
        return players;
    }
}
