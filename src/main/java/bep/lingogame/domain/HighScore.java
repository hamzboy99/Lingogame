package bep.lingogame.domain;

public class HighScore {
    public transient Player player;

    public HighScore( Player player) {
        this.player = player;
    }
}
