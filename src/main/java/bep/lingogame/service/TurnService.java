package bep.lingogame.service;

import bep.lingogame.domain.Turn;
import bep.lingogame.repository.TurnRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TurnService {
    private final transient TextDeserializer textDeserializer;
    private final transient TurnRepository turnRepository;
    private final transient List<String> allwords;
    private transient int currentLetterExistenceInWord;

    public TurnService(final TurnRepository turnRepository, final TextDeserializer textDeserializer) throws FileNotFoundException {
        this.turnRepository = turnRepository;
        this.textDeserializer = textDeserializer;
        this.allwords = textDeserializer.deserialize("src/main/resources/static/basiswoorden.txt");
    }


    public String returnRandomWord(final int wordLength) {
        final List<String> checkedWords = sortWords(wordLength);
        final int rnd = new Random().nextInt(checkedWords.size());
        final String word = checkedWords.get(rnd);
        System.out.println(word);
        return word;
    }

    public List<String> sortWords(final int length) {
        final List<String> wordslist = new ArrayList<>();
        for (final String data : allwords) {
            if (data.length() == length) {
                wordslist.add(data);
            }
        }
        System.out.println(wordslist);
        return wordslist;
    }

    public String checkGuessedLetters(final String guessedWord, final String woord) {
        int i = 0;
        String correctLetters = "";

        for (final char letter : woord.toCharArray()) {
            final char currentLetter = guessedWord.charAt(i);
            if (letter == guessedWord.charAt(i)) { //Checkt of de letter op de goeie plek staat
                System.out.println(letter + " correct");
                correctLetters += letter;
            } else if (letter != guessedWord.charAt(i)) {
                correctLetters = checkPresentOrAbsent(currentLetter, correctLetters, woord);
            }
            i++;
        }
        return correctLetters;
    }

    public String checkPresentOrAbsent(final char currentLetter, String correctLetters, final String word) {
        currentLetterExistenceInWord = checkExistence(currentLetter, correctLetters, word);
        if ((word.indexOf(currentLetter)) >= 0) { //Checkt of de letter erin zit.
            if ((correctLetters.indexOf(currentLetter)) >= 0) { //Checkt of de letter al is geweest.
                System.out.println(currentLetter + " present, deze letter komt " + currentLetterExistenceInWord + " keer voor");
            } else { //Als de letter niet is gekozen.
                System.out.println(currentLetter + " present, deze letter zit niet op de goede plek");
            }
        } else {
            System.out.println(currentLetter + " absent, deze letter zit niet in het woord");
        }
        correctLetters += '_';
        return correctLetters;
    }

    public int checkExistence(final char currentLetter, final String correctLetters, final String randomwoord) {
        int index = randomwoord.indexOf(currentLetter);

        currentLetterExistenceInWord = 0;
        if ((correctLetters.indexOf(currentLetter)) >= 0) { //Checkt of de letter al aanbod is gekomen.
            while (index >= 0) {
                index = randomwoord.indexOf(currentLetter, index + 1);
                currentLetterExistenceInWord += 1;
            }
        }
        return currentLetterExistenceInWord;
    }

    public void findById(final Long id) {
        turnRepository.findById(id);
    }

    public Turn createNew(final Turn turnRestRequest) {
        final Turn turn = new Turn(null, turnRestRequest.chances, turnRestRequest.status, turnRestRequest.guessedWord, turnRestRequest.wordLength, LocalDateTime.now());
        turnRepository.save(turn);
        return turn;
    }

}
