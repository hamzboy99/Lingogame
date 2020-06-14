package bep.lingogame;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Turn {
    @Id
    @GeneratedValue
    public Long id;
    public int aantalfout;
    public String word;
    public String guessedWord;
    public int wordLength;
    @ManyToOne
    private Game game;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Turn() {
    }

    public Turn(Long id, int aantalfout, String word, String guessedWord, int wordLength, Game game, LocalDateTime createdAt) {
        this.id = id;
        this.aantalfout = aantalfout;
        this.word = word;
        this.guessedWord = guessedWord;
        this.wordLength = wordLength;
        this.game = game;
        this.createdAt = createdAt;
    }

}