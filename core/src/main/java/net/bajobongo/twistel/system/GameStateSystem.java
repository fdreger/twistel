package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.bajobongo.twistel.component.Element;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.game.GameStateService;
import net.bajobongo.twistel.game.HeadLocator;
import net.bajobongo.twistel.game.InitialPattern;
import net.bajobongo.twistel.game.PlayerInput;
import net.bajobongo.twistel.infrastructure.GameStage;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.EnumMap;

public class GameStateSystem extends EntitySystem {

    private final GameStateService gameStateService;
    private final InitialPattern initialPattern;
    private final HeadLocator headLocator;
    private final EnumMap<Element.ElementType, Integer> counts = new EnumMap<>(Element.ElementType.class);
    private final TweenService tweenService;
    private final GameStage gameStage;

    @WithFactory
    public GameStateSystem(GameStateService gameStateService, InitialPattern initialPattern, HeadLocator headLocator, TweenService tweenService, GameStage gameStage) {
        this.gameStateService = gameStateService;
        this.initialPattern = initialPattern;
        this.headLocator = headLocator;
        this.tweenService = tweenService;
        this.gameStage = gameStage;
    }

    public void update(float deltaTime) {
        if (gameStateService.isWaitingForStart()) {
            if (gameStage.isStartClicked()) {
                initialPattern.fillUp();
                gameStateService.play();
            }
        } else {
            gameStateService.update(deltaTime);

            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                gameStateService.endGame();
            }

            if (tweenService.isAnythingTweening()) {
                return;
            }
            // calculate counts of elements
            for (Element.ElementType elementType : Element.ElementType.values()) {
                counts.put(elementType, 0);
            }
            Entity head = headLocator.findHead();
            Entity current = head;
            while (current != null) {
                Entity elementEntity = current.getComponent(Place.class).getElement();
                if (elementEntity != null) {
                    Element.ElementType type = elementEntity.getComponent(Element.class).type;
                    counts.put(type, counts.get(type) + 1);
                }
                current = current.getComponent(Place.class).getNext();
            }
            // if each element has count below 3, game is over
            boolean gameOver = true;
            for (Element.ElementType elementType : Element.ElementType.values()) {
                if (counts.get(elementType) >= 3) {
                    gameOver = false;
                    break;
                }
            }
            if (gameOver) {
                gameStateService.endGame();
            }
        }
    }
}
