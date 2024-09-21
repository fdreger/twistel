package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.game.HeadLocator;
import net.bajobongo.twistel.game.PlayerInput;
import net.bajobongo.twistel.infrastructure.GameCamera;
import net.snowyhollows.bento.annotation.WithFactory;

public class ClickToRemoveSystem extends EntitySystem {
    private final PlayerInput playerInput;

    @WithFactory
    public ClickToRemoveSystem(HeadLocator headLocator, GameCamera camera, PlayerInput playerInput) {
        this.playerInput = playerInput;
    }

    @Override
    public void update(float deltaTime) {

        Entity clickedPlaceEntity = playerInput.getJustClickedPlace();
        if (clickedPlaceEntity != null) {
            Place clickedPlace = clickedPlaceEntity.getComponent(Place.class);
            Entity element = clickedPlace.getElement();
            if (element != null) {
                getEngine().removeEntity(element);
                clickedPlace.setElement(null);
            }
        }
    }
}
