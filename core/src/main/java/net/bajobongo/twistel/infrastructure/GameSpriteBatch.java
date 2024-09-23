package net.bajobongo.twistel.infrastructure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.snowyhollows.bento.annotation.WithFactory;

public class GameSpriteBatch extends SpriteBatch {
    @WithFactory
    public GameSpriteBatch() {
        super();
    }

    public void drawCentered(TextureRegion textureRegion, float x, float y, float width, float height) {
        draw(textureRegion, x - width / 2, y - height / 2, width, height);
    }
}
