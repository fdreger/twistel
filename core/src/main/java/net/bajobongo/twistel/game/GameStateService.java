package net.bajobongo.twistel.game;

import net.snowyhollows.bento.annotation.WithFactory;

public class GameStateService {

    public enum Situation {
        PLAYING, WAITING_FOR_START, HIGH_SCORE
    }

    public boolean isWaitingForStart() {
        return situation == Situation.WAITING_FOR_START || situation == Situation.HIGH_SCORE;
    }

    public boolean isPlaying() {
        return situation == Situation.PLAYING;
    }

    public boolean isHighScore() {
        return situation == Situation.HIGH_SCORE;
    }

    private int highScore = 0;
    private int score = 0;
    private Situation situation = Situation.WAITING_FOR_START;

    @WithFactory
    public GameStateService() {
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void endGame() {
        if (highScore < score) {
            highScore = score;
            situation = Situation.HIGH_SCORE;
        } else {
            situation = Situation.WAITING_FOR_START;
        }
    }

    public void play() {
        score = 0;
        situation = Situation.PLAYING;
    }
}
