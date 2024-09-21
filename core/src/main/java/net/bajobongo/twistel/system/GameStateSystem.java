package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.bajobongo.twistel.component.Element;
import net.bajobongo.twistel.component.ElementType;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.game.GameStateService;
import net.bajobongo.twistel.game.HeadLocator;
import net.bajobongo.twistel.game.InitialPattern;
import net.bajobongo.twistel.game.PlayerInput;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.EnumMap;

public class GameStateSystem extends EntitySystem {
    private final GameStateService gameStateService;
    private final PlayerInput playerInput;
    private final InitialPattern initialPattern;
    private final HeadLocator headLocator;
    private final EnumMap<ElementType, Integer> counts = new EnumMap<>(ElementType.class);

    @WithFactory
    public GameStateSystem(GameStateService gameStateService, PlayerInput playerInput, InitialPattern initialPattern, HeadLocator headLocator) {
        this.gameStateService = gameStateService;
        this.playerInput = playerInput;
        this.initialPattern = initialPattern;
        this.headLocator = headLocator;
    }

    public void update(float deltaTime) {
        if (gameStateService.isWaitingForStart() && playerInput.justClickedStart()) {
            initialPattern.fillUp();
            gameStateService.play();
        } else {
            // calculate counts of elements
            for (ElementType elementType : ElementType.values()) {
                counts.put(elementType, 0);
            }
            Entity head = headLocator.findHead();
            Entity current = head;
            while (current != null) {
                Entity elementEntity = current.getComponent(Place.class).getElement();
                if (elementEntity != null) {
                    ElementType type = elementEntity.getComponent(Element.class).type;
                    counts.put(type, counts.get(type) + 1);
                }
                current = current.getComponent(Place.class).getNext();
            }
            // if each element has count below 3, game is over
            boolean gameOver = true;
            for (ElementType elementType : ElementType.values()) {
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
