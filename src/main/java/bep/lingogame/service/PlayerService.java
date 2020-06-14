package bep.lingogame.service;

import bep.lingogame.domain.Player;
import bep.lingogame.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayerService {
    private final transient PlayerRepository playerRepository;

    public PlayerService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(final Long id) {
        return playerRepository.findById(id);
    }

    public Player addToScore(final Player player, final int score) {
        final int newScore = player.score + score;

        final Player updatedPlayer =  new Player (player.id, player.name, newScore, player.createdAt);
        return playerRepository.save(updatedPlayer);
    }

    public Player createNew(final Player playerRestRequest) {
        final Player player = new Player(null, playerRestRequest.name, 0, LocalDateTime.now());
        playerRepository.save(player);
        return player;
    }
}