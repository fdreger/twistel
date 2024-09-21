package net.bajobongo.twistel.infrastructure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.snowyhollows.bento.annotation.WithFactory;

public class GameSpriteBatch extends SpriteBatch {
    @WithFactory
    public GameSpriteBatch() {
        super();
    }
}
