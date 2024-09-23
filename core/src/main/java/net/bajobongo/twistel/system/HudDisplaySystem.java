package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import net.bajobongo.twistel.assets.AssetsService;
import net.bajobongo.twistel.game.GameStateService;
import net.bajobongo.twistel.infrastructure.GameSpriteBatch;
import net.snowyhollows.bento.annotation.WithFactory;

public class HudDisplaySystem extends EntitySystem {

    private final GameSpriteBatch batch;
    private final AssetsService assetsService;
    private final GameStateService gameStateService;

    @WithFactory
    public HudDisplaySystem(GameSpriteBatch batch, AssetsService assetsService, GameStateService gameStateService) {
        this.batch = batch;
        this.assetsService = assetsService;
        this.gameStateService = gameStateService;
    }

    @Override
    public void update(float deltaTime) {
//        BitmapFont font = assetsService.getFont();
//        batch.begin();
//        batch.setShader(assetsService.getFontShader());
//        batch.setColor(Color.WHITE);
//        font.getData().setScale(1.6f);
//        font.draw(batch, "" + gameStateService.getScore(), 50, 110);
//        if (gameStateService.isWaitingForStart() || true) {
//            font.getData().setScale(1.2f);
//            if (gameStateService.isWaitingForStart()) {
//                font.draw(batch, "Start", 350, 400);
//                if (gameStateService.getHighScore() > 0) {
//                    font.draw(batch, "High Score: " + gameStateService.getHighScore(), 270, 320);
//                }
//            }
//        }
//        batch.end();

    }
}
