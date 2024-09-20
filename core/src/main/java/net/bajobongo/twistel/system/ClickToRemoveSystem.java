package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;

public class ClickToRemoveSystem extends EntitySystem {

    private Entity head;
    private final Camera camera;

    public ClickToRemoveSystem(Entity head, Camera camera) {
        this.head = head;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.justTouched()) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos);

            Entity current = head;
            while (current != null) {
                RectangleComponent rect = current.getComponent(RectangleComponent.class);
                if (rect != null && rect.contains(mousePos.x, mousePos.y)) {
                    Place place = current.getComponent(Place.class);
                    if (place != null) {
                        Entity element = place.getElement();
                        if (element != null) {
                            getEngine().removeEntity(element);
                            place.setElement(null);
                        }
                        break;
                    }
                }
                current = current.getComponent(Place.class).getNext();
            }
        }
    }
}
