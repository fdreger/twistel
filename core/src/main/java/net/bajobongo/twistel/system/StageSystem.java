package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.infrastructure.GameStage;
import net.snowyhollows.bento.annotation.WithFactory;

public class StageSystem extends EntitySystem {
    private final GameStage gameStage;

    @WithFactory
    public StageSystem(GameStage gameStage) {
        this.gameStage = gameStage;
    }


    @Override
    public void update(float deltaTime) {
        gameStage.act(deltaTime);
        gameStage.draw();
    }

    public GameStage getGameStage() {
        return gameStage;
    }
}
