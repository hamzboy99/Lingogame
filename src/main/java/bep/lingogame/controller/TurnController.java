package bep.lingogame.controller;

import bep.lingogame.domain.Game;
import bep.lingogame.domain.Player;
import bep.lingogame.domain.Turn;
import bep.lingogame.service.GameService;
import bep.lingogame.service.PlayerService;
import bep.lingogame.service.TurnService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/turn")
public class TurnController {
    private final transient TurnService turnService;
    private final transient PlayerService playerService;
    private final transient PlayerController playerController;
    private final transient GameService gameService;
    private final transient GameController gameController;
    private transient String word;
    private transient String correctLetters;
    private transient int aantalfout = 0;
    private transient int wordLength = 5;
    private transient int score = 50;

    public TurnController(final TurnService turnService, final PlayerService playerService, final PlayerController playerController, final GameService gameService, GameController gameController) {
        this.turnService = turnService;
        this.playerService = playerService;
        this.playerController = playerController;
        this.gameService = gameService;
        this.gameController = gameController;
    }

    @GetMapping
    // Deze functie geeft als returnwaarde de eerste letter van de woord + info over het woord.
    public String getRandomWord() {
        aantalfout = 0;
        correctLetters = "";
        final StringBuilder aantalStreepjes = new StringBuilder();
        word = turnService.returnRandomWord(wordLength);
        for (int i = 0; i < word.length() - 1; i++) {
            aantalStreepjes.append(" _ ");
        }
        final String returnWaarde = word.substring(0, 1) + aantalStreepjes + " " + word.length() + " tekens lang";
        System.out.println(returnWaarde);
        return returnWaarde;
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(final @RequestBody Turn turn) {
        turnService.cleanFeedback();
        turnService.updateGuessedWord(turn.guessedWord);
        wordLength = turn.wordLength;
        final Player currentPlayer = playerService.findById(playerController.returnPlayerId());
        final Game currentGame = gameService.findById(playerController.returnGameId());

        if (aantalfout < 5) {
            if (turn.guessedWord.charAt(0) != word.charAt(0)) { //Als het woord met een andere letter begint.
                aantalfout++;
                turnService.updateAantalFout(aantalfout);
                turnService.createNew(currentGame);
                return "Vul een woord in die begint met de letter " + word.charAt(0) + "\nAantalfout: " + aantalfout;
            } else if (turn.guessedWord.equals(word)) {
                aantalfout = 0;
                turnService.updateAantalFout(aantalfout);
                score += 50;
                correctLetters = "";
                turnService.createNew(currentGame);
                return "Goed geraden \n" + word + "\nAantalfout: " + aantalfout;
            } else if (turn.guessedWord.length() != wordLength) {
                aantalfout++;
                turnService.updateAantalFout(aantalfout);
                turnService.createNew(currentGame);
                return "Vul een woord in met de wordlengte van " + wordLength + " letters\nAantalfout: " + aantalfout;
            } else { //Als het woord niet in een keer is geraden.
                final String guessedWord = turn.guessedWord;
                correctLetters = turnService.checkGuessedLetters(guessedWord, word);
                aantalfout++;
                turnService.updateAantalFout(aantalfout);
                turnService.createNew(currentGame);
            }
            return correctLetters + " correcte letters \nAantalfout: " + aantalfout + "\n" + turnService.getFeedback();
        } else { //Als de beurten op zijn, dus aantalfout 5.
            score = score - (aantalfout * 10);
            playerService.addToScore(currentPlayer, score);
            turnService.createNew(currentGame);
            return "Game over \nScore: " + score;
        }
    }
}
