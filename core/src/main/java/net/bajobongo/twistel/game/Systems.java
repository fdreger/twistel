package net.bajobongo.twistel.game;

import net.bajobongo.twistel.infrastructure.Ashley;
import net.bajobongo.twistel.system.*;
import net.snowyhollows.bento.annotation.WithFactory;

public class Systems {
    private final Ashley ashley;
    private final DisplaySystem displaySystem;
    private final LogDebugElements logDebugElements;
    private final ClickToRemoveSystem clickToRemoveSystem;
    private final SlideElementsSystem slideElementsSystem;
    private final ClickToTwistSystem clickToTwistSystem;
    private final TweeningSystem tweeningSystem;
    private final HudDisplaySystem hudDisplaySystem;
    private final GameStateSystem gameStateSystem;
    private final GameplayMatchesSystem gameplayMatchesSystem;
    private final StageSystem stageSystem;

    @WithFactory
    public Systems(Ashley ashley,
                   DisplaySystem displaySystem,
                   LogDebugElements logDebugElements,
                   ClickToRemoveSystem clickToRemoveSystem,
                   SlideElementsSystem slideElementsSystem,
                   ClickToTwistSystem clickToTwistSystem,
                   TweeningSystem tweeningSystem,
                   HudDisplaySystem hudDisplaySystem,
                   GameStateSystem gameStateSystem,
                   GameplayMatchesSystem gameplayMatchesSystem, StageSystem stageSystem) {
        this.ashley = ashley;
        this.displaySystem = displaySystem;
        this.logDebugElements = logDebugElements;
        this.clickToRemoveSystem = clickToRemoveSystem;
        this.slideElementsSystem = slideElementsSystem;
        this.clickToTwistSystem = clickToTwistSystem;
        this.tweeningSystem = tweeningSystem;
        this.hudDisplaySystem = hudDisplaySystem;
        this.gameStateSystem = gameStateSystem;
        this.gameplayMatchesSystem = gameplayMatchesSystem;
        this.stageSystem = stageSystem;
    }

    public void init() {
        ashley.getEngine().addSystem(displaySystem);
        ashley.getEngine().addSystem(stageSystem);
//        ashley.getEngine().addSystem(clickToRemoveSystem);
//        ashley.getEngine().addSystem(logDebugElements);
        ashley.getEngine().addSystem(clickToTwistSystem);
        ashley.getEngine().addSystem(gameplayMatchesSystem);
        ashley.getEngine().addSystem(slideElementsSystem);
        ashley.getEngine().addSystem(tweeningSystem);
        ashley.getEngine().addSystem(hudDisplaySystem);
        ashley.getEngine().addSystem(gameStateSystem);

    }
}
