package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;

public class MoveElementsSystem extends EntitySystem {
    private Entity head;

    public MoveElementsSystem(Entity head) {
        this.head = head;
    }


    @Override
    public void update(float deltaTime) {
        Entity current = head;
        while (current != null) {
            Place place = current.getComponent(Place.class);
            // snap elements to places
            if (place != null) {
                Entity element = place.getElement();
                if (element != null) {
                    RectangleComponent elementRectangle = element.getComponent(RectangleComponent.class);
                    RectangleComponent placeRectangle = current.getComponent(RectangleComponent.class);
                    elementRectangle.x = placeRectangle.x;
                    elementRectangle.y = placeRectangle.y;
                }
            }
            current = place.getNext();
        }
    }
}
