package bep.lingogame.repository;

import bep.lingogame.Turn;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TurnRepository extends Repository<Turn, Long> {
    List<Turn> findAll();
    void findById(Long id);
    void save(Turn turn);
}
