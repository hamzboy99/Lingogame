package bep.lingogame;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Game {
    @Id
    @GeneratedValue
    public  Long id;
    @OneToOne
    private  Player player;
    @CreationTimestamp
    public  LocalDateTime createdAt;

    public Game() {
    }

    public Game( Long id,  Player player,  LocalDateTime createdAt) {
        this.id = id;
        this.player = player;
        this.createdAt = createdAt;
    }
}
