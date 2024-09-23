package net.bajobongo.twistel.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import net.bajobongo.twistel.infrastructure.GameAssetsManager;
import net.snowyhollows.bento.annotation.WithFactory;

public class AssetsService {
    private static final String WHITE_CIRCLE_PNG = "white-circle.png";
    private static final String FONT_NAME = "YanoneKaffeesatz-msdf.fnt";
    private static final String SONG_NAME = "snd/song-game.ogg";
    private static final String PLINK = "snd/plink-game.wav";
    private static final String WOOSH = "snd/woosh-game.wav";
    private static final String GAME_OVER = "snd/game-over-game.wav";

    private final GameAssetsManager gameAssetsManager;
    private TextureRegion circle;
    private BitmapFont font;
    private ShaderProgram fontShader;
    private Music music;
    private Sound plink;
    private Sound gameOver;
    private Sound woosh;

    @WithFactory
    public AssetsService(GameAssetsManager gameAssetsManager) {
        this.gameAssetsManager = gameAssetsManager;
    }

    public void init() {
        gameAssetsManager.load(WHITE_CIRCLE_PNG, Texture.class);
        BitmapFontLoader.BitmapFontParameter bitmapFontParameter = new BitmapFontLoader.BitmapFontParameter();
        bitmapFontParameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFontParameter.minFilter = Texture.TextureFilter.Linear;
        bitmapFontParameter.genMipMaps = true;
        gameAssetsManager.load(FONT_NAME, BitmapFont.class, bitmapFontParameter);
        gameAssetsManager.load(SONG_NAME, Music.class);
        gameAssetsManager.load(PLINK, Sound.class);
        gameAssetsManager.load(GAME_OVER, Sound.class);
        gameAssetsManager.load(WOOSH, Sound.class);
        gameAssetsManager.finishLoading();
        Texture circleTexture = gameAssetsManager.get(WHITE_CIRCLE_PNG, Texture.class);
        font = gameAssetsManager.get(FONT_NAME, BitmapFont.class);
        circle = new TextureRegion(circleTexture, circleTexture.getWidth(), circleTexture.getHeight());
        fontShader = new ShaderProgram(Gdx.files.internal("font-vert.glsl"), Gdx.files.internal("font-frag.glsl"));
        music = gameAssetsManager.get(SONG_NAME, Music.class);
        plink = gameAssetsManager.get(PLINK, Sound.class);
        gameOver = gameAssetsManager.get(GAME_OVER, Sound.class);
        woosh = gameAssetsManager.get(WOOSH, Sound.class);
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

    public Music getMusic() {
        return music;
    }

    public Sound getPlink() {
        return plink;
    }

    public Sound getGameOver() {
        return gameOver;
    }

    public Sound getWoosh() {
        return woosh;
    }
}
