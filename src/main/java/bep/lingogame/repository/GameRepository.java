package bep.lingogame.repository;

import bep.lingogame.Game;
import org.springframework.data.repository.Repository;

public interface GameRepository extends Repository<Game, Long> {
    void save(Game game);

    Game findById(Long id);
}
