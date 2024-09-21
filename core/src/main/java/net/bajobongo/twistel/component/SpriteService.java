package net.bajobongo.twistel.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import net.bajobongo.twistel.infrastructure.GameAssetsManager;
import net.snowyhollows.bento.annotation.WithFactory;
import org.w3c.dom.Text;

public class SpriteService {
    private static final String WHITE_CIRCLE_PNG = "white-circle.png";
    public static final String FONT_NAME = "YanoneKaffeesatz-msdf.fnt";

    private final GameAssetsManager gameAssetsManager;
    private TextureRegion circle;
    private BitmapFont font;
    private ShaderProgram fontShader;

    @WithFactory
    public SpriteService(GameAssetsManager gameAssetsManager) {
        this.gameAssetsManager = gameAssetsManager;
    }

    public void init() {
        gameAssetsManager.load(WHITE_CIRCLE_PNG, Texture.class);
        BitmapFontLoader.BitmapFontParameter bitmapFontParameter = new BitmapFontLoader.BitmapFontParameter();
        bitmapFontParameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFontParameter.minFilter = Texture.TextureFilter.Linear;
        bitmapFontParameter.genMipMaps = true;
        gameAssetsManager.load(FONT_NAME, BitmapFont.class, bitmapFontParameter);
        gameAssetsManager.finishLoading();
        Texture circleTexture = gameAssetsManager.get(WHITE_CIRCLE_PNG, Texture.class);
        font = gameAssetsManager.get("YanoneKaffeesatz-msdf.fnt", BitmapFont.class);
        circle = new TextureRegion(circleTexture, circleTexture.getWidth(), circleTexture.getHeight());
        fontShader = new ShaderProgram(Gdx.files.internal("font-vert.glsl"), Gdx.files.internal("font-frag.glsl"));
        if (!fontShader.isCompiled()) {
            throw new RuntimeException("compilation failed:\n" + fontShader.getLog());
        }

    }

    public TextureRegion getCircle() {
        return circle;
    }

    public BitmapFont getFont() {
        return font;
    }

    public ShaderProgram getFontShader() {
        return fontShader;
    }
}
