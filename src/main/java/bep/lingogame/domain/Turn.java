package bep.lingogame.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Turn {
    @Id
    @GeneratedValue
    public  Long id;
    public  int chances;
    public  String status;
    public  String guessedWord;
    public  int wordLength;
    @CreationTimestamp
    public  LocalDateTime createdAt;

    public Turn() {
    }

    public Turn( Long id,  int chances,  String status,  String guessedWord,  int wordLength,  LocalDateTime createdAt) {
        this.id = id;
        this.chances = chances;
        this.status = status;
        this.guessedWord = guessedWord;
        this.wordLength = wordLength;
        this.createdAt = createdAt;
    }

}