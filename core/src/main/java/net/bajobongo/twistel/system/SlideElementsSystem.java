package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.component.Place;

public class SlideElementsSystem extends EntitySystem {

    private Entity head;

    public SlideElementsSystem(Entity head) {
        this.head = head;
    }

    @Override
    public void update(float deltaTime) {
        Entity current = head;
        while (current != null) {
            Place place = current.getComponent(Place.class);
            if (place != null) {
                Entity element = place.getElement();
                // if element is null, try to find the next element
                if (element == null) {
                    Entity next = place.getNext();
                    if (next != null) {
                        Entity nextElement = next.getComponent(Place.class).getElement();
                        if (nextElement != null) {
                            place.setElement(nextElement);
                            next.getComponent(Place.class).setElement(null);
                        }
                    }
                }
            }
            current = current.getComponent(Place.class).getNext();
        }
    }
}
