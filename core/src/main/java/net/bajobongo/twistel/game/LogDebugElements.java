package net.bajobongo.twistel.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.component.Element;
import net.bajobongo.twistel.component.Place;
import net.snowyhollows.bento.annotation.WithFactory;

public class LogDebugElements extends EntitySystem {

    private final PlayerInput playerInput;
    private final HeadLocator headLocator;

    @WithFactory
    public LogDebugElements(PlayerInput playerInput, HeadLocator headLocator) {
        this.playerInput = playerInput;
        this.headLocator = headLocator;
    }

    @Override
    public void update(float deltaTime) {
        if (playerInput.getJustClickedPlace() != null) {
            Entity current = headLocator.findHead();

            while (current != null) {
                Element.ElementType currentType = current.getComponent(Place.class).getElement() == null ? null :  current.getComponent(Place.class).getElement().getComponent(Element.class).type;
                System.out.println(currentType);
                current = current.getComponent(Place.class).getNext();
            }
        }
    }
}
