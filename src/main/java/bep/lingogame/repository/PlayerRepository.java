package bep.lingogame.repository;

import bep.lingogame.Player;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayerRepository extends Repository<Player, Long> {
    List<Player> findAll();
    Player findById(Long id);
    Player save(Player player);
}

