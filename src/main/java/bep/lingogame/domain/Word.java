package bep.lingogame.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Word {
    @Id
    @GeneratedValue
    public Long id;
    private String word;
    private int amountOfLetters;

    public Word() {
    }
}
