package net.bajobongo.twistel.infrastructure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import net.snowyhollows.bento.annotation.WithFactory;


public class GameViewport extends FitViewport {
    private final SpriteBatch spriteBatch;

    @WithFactory
    public GameViewport(GameCamera gameCamera, GameSpriteBatch spriteBatch) {
        super(800, 600);
        this.spriteBatch = spriteBatch;
        setCamera(gameCamera);
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        super.update(screenWidth, screenHeight, centerCamera);
        spriteBatch.setProjectionMatrix(getCamera().combined);
    }
}
