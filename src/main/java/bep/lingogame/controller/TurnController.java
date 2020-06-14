package bep.lingogame.controller;

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
    private transient int aantalfout = 0;
    private transient String word;
    private transient String correctLetters;
    private transient int wordLength = 5;
    private transient int score = 50;

    public TurnController(final TurnService turnService, final PlayerService playerService, final PlayerController playerController, final GameService gameService) {
        this.turnService = turnService;
        this.playerService = playerService;
        this.playerController = playerController;
        this.gameService = gameService;
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
        wordLength = turn.wordLength;

        if (aantalfout < 5) {
            if (turn.guessedWord.charAt(0) != word.charAt(0)) { //Als het woord met een andere letter begint.
                aantalfout++;
                return "Vul een woord in die begint met de letter " + word.charAt(0) + "\nAantalfout: " + aantalfout;
            } else if (turn.guessedWord.equals(word)) {
                aantalfout = 0;
                score += 50;
                correctLetters = "";
                System.out.println("Correct");
                return "Goed geraden \n" + word + "\nAantalfout: " + aantalfout;
            } else if (turn.guessedWord.length() != wordLength) {
                aantalfout++;
                return "Vul een woord in met de wordlengte van " + wordLength + " letters\nAantalfout: " + aantalfout;
            } else { //Als het woord niet in een keer is geraden.
                final String guessedWord = turn.guessedWord;
                correctLetters = turnService.checkGuessedLetters(guessedWord, word);
                aantalfout++;
                System.out.println("Aantal fout: " + aantalfout);
            }
            System.out.println(correctLetters + " je gok");
            return correctLetters + " correcte letters \n Aantalfout: " + aantalfout;
        } else { //Als de beurten op zijn, dus aantalfout 5.
            score = score - (aantalfout * 10);
            final Player player = playerService.findById(playerController.returnPlayerId());
            playerService.addToScore(player, score);
            System.out.println(player.name + player.score);
            gameService.createNew(player);
            return "Game over \nScore: " + score;
        }
    }

}
