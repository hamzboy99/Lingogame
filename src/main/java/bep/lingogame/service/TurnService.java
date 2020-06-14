package bep.lingogame.service;

import bep.lingogame.Game;
import bep.lingogame.Turn;
import bep.lingogame.repository.TurnRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TurnService {
    private final transient WordRefiner wordRefiner;
    private final transient TurnRepository turnRepository;
    private final transient GameService gameService;
    private final transient List<String> allwords;
    private final ArrayList<String> response = new ArrayList<>();
    private String word;
    private String getGuessedWord;
    private int getwordLength;
    private int totaalFout = 0;

    public TurnService(final TurnRepository turnRepository, final WordRefiner wordRefiner, GameService gameService) throws FileNotFoundException {
        this.turnRepository = turnRepository;
        this.wordRefiner = wordRefiner;
        this.allwords = wordRefiner.refine("src/main/resources/static/basiswoorden.txt");
        this.gameService = gameService;
    }

    public String returnRandomWord(final int wordLength) {
        final List<String> checkedWords = sortWords(wordLength);
        final int rnd = new Random().nextInt(checkedWords.size());
        word = checkedWords.get(rnd);
        getwordLength = wordLength;
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
                response.add(letter + " correct");
                correctLetters += letter;
            } else if (letter != guessedWord.charAt(i)) {
                correctLetters = checkPresentOrAbsent(currentLetter, correctLetters, woord);
            }
            i++;
        }
        return correctLetters;
    }

    public String checkPresentOrAbsent(final char currentLetter, String correctLetters, final String word) {
        if ((word.indexOf(currentLetter)) >= 0) { //Checkt of de letter erin zit.
            response.add(currentLetter + " present");
        } else {
            response.add(currentLetter + " absent");
        }
        correctLetters += '_';
        return correctLetters;
    }

    public void updateAantalFout(int aantalFout) {
        totaalFout = aantalFout;
    }

    public void updateGuessedWord(String guessedWord) {
        getGuessedWord = guessedWord;
    }

    public ArrayList<String> getFeedback() {
        return response;
    }

    public void cleanFeedback() {
        response.clear();
    }

    public Turn createNew(final Game game) {
        final Turn turn = new Turn(null, totaalFout, word, getGuessedWord, getwordLength, game, LocalDateTime.now());
        turnRepository.save(turn);
        return turn;
    }

}
