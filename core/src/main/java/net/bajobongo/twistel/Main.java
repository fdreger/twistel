package net.bajobongo.twistel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.bajobongo.twistel.assets.AssetsServiceFactory;
import net.bajobongo.twistel.game.InitialPatternFactory;
import net.bajobongo.twistel.game.SystemsFactory;
import net.bajobongo.twistel.infrastructure.*;
import net.snowyhollows.bento.Bento;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Bento bento = Bento.createRoot();
    Ashley ashley;
    Viewport gameViewport;

    @Override
    public void create() {
        ashley = bento.get(AshleyFactory.IT);
        gameViewport = bento.get(GameViewportFactory.IT);
        bento.get(InitialPatternFactory.IT).init();
        bento.get(SystemsFactory.IT).init();
        bento.get(AssetsServiceFactory.IT).init();
    }


    @Override
    public void render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.26f, 1f);
        ashley.getEngine().update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
