package net.bajobongo.twistel.game;

import net.bajobongo.twistel.assets.AssetsService;
import net.snowyhollows.bento.annotation.WithFactory;

public class GameStateService {

    private final AssetsService assetsService;
    public static final float BONUS_BARRIER = 200f;

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

    public float getBonusRatio() {
        return bonus / BONUS_BARRIER;
    }

    private int highScore = 0;
    private int score = 0;
    private int bonus = 0;
    private Situation situation = Situation.WAITING_FOR_START;

    private final InitialPattern initialPattern;

    @WithFactory
    public GameStateService(AssetsService assetsService, InitialPattern initialPattern) {
        this.assetsService = assetsService;
        this.initialPattern = initialPattern;
    }

    public void addScore(int score) {
        this.score += score;
        this.bonus += score;
        while (bonus > BONUS_BARRIER) {
            bonus -= BONUS_BARRIER;
            initialPattern.fillUp();
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void endGame() {
        if (isPlaying()) {
            assetsService.getMusic().stop();
            assetsService.getGameOver().play(0.9f);
        }
        if (highScore < score) {
            highScore = score;
            bonus = 0;
            situation = Situation.HIGH_SCORE;
        } else {
            bonus = 0;
            situation = Situation.WAITING_FOR_START;
        }
    }

    public void play() {
        score = 0;
        bonus = 0;
        assetsService.getMusic().play();
        situation = Situation.PLAYING;
    }

    float updated = 0;
    public void update(float delta) {
        updated += delta;
        while (updated > 0.1f) {
            updated -= 0.1f;
            if (bonus > 0) {
                bonus -= 1;
            }
        }
    }
}
