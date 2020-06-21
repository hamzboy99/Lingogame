package bep.lingogame.service;

import bep.lingogame.domain.Game;
import bep.lingogame.domain.Player;
import bep.lingogame.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {
    private final transient GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game findById(final Long id) {
        return gameRepository.findById(id);
    }


    public Game createNew(final Player player) {
        final Game game = new Game(null, player, LocalDateTime.now());
        gameRepository.save(game);
        return game;
    }
}
