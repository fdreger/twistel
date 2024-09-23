package net.bajobongo.twistel.infrastructure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import net.bajobongo.twistel.assets.AssetsService;
import net.snowyhollows.bento.annotation.WithFactory;

public class GameStage extends Stage {
    private final AssetsService assetsService;
    private Button startGameButton;
    private boolean startClicked;
    private Button restartGameButton2;
    private Label highScoreLabel;
    private Table startTable;
    private Table restartTable;
    private Table hiscoreTable;
    private Label scoreLabel;

    public void showScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public boolean isStartClicked() {
        return startClicked;
    }

    public void hideGui() {
        startTable.remove();
        restartTable.remove();
        hiscoreTable.remove();
    }

    public void showRestart() {
        startTable.remove();
        hiscoreTable.remove();
        addActor(restartTable);
        startClicked = false;
    }

    public void showStart() {
        restartTable.remove();
        hiscoreTable.remove();
        addActor(startTable);
        startClicked = false;
    }

    public void showHighScore(int score) {
        startTable.remove();
        restartTable.remove();
        addActor(hiscoreTable);
        highScoreLabel.setText("New High Score: " + score);
        startClicked = false;
    }

    @WithFactory
    public GameStage(GameViewport gameViewport, AssetsService assetsService) {
        super(gameViewport);
        this.assetsService = assetsService;
        Gdx.input.setInputProcessor(this);
    }

    public void init() {
        Skin skin = assetsService.getSkin();

        Table scoreTable = new Table();
        scoreTable.setFillParent(true);
        scoreLabel = new Label("Score: " + 0, skin);
        scoreTable.add(scoreLabel).left().bottom().expand();
        addActor(scoreTable);

        startTable = new Table();
        startTable.setFillParent(true);
        startGameButton = new Button(skin);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startClicked = true;
            }
        });
        startGameButton.add("Start");
        startTable.add(startGameButton).colspan(2).center().row();


        restartTable = new Table();
        restartTable.setFillParent(true);
        restartGameButton2 = new Button(skin);
        restartGameButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startClicked = true;
            }
        });
        restartGameButton2.add("Restart");
        restartTable.add(restartGameButton2).colspan(2).center().row();

        hiscoreTable = new Table();
        hiscoreTable.setFillParent(true);
        highScoreLabel = new Label("New Hi: " + 123, skin);
        hiscoreTable.add(highScoreLabel).colspan(2).center().row();
        restartGameButton2 = new Button(skin);
        restartGameButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startClicked = true;
            }
        });
        restartGameButton2.add("Restart");
        hiscoreTable.add(restartGameButton2).colspan(2).center().row();

        showStart();
    }
}
