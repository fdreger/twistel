package net.bajobongo.twistel.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import net.bajobongo.twistel.component.Clickable;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;
import net.bajobongo.twistel.infrastructure.GameCamera;
import net.bajobongo.twistel.infrastructure.GameViewport;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerInput {

    HeadLocator headLocator;
    private final GameViewport camera;

    private final List<Entity> mouseOverPlaces = new ArrayList<>();


    @WithFactory
    public PlayerInput(HeadLocator headLocator, GameViewport camera) {
        this.headLocator = headLocator;
        this.camera = camera;
    }

    public void recalculate() {

    }



    public boolean justClickedStart() {
        return (Gdx.input.justTouched() && getJustClickedPlace() == null);
    }

    public void gatherHoveredPlaces(Set<Entity> entities) {
        headLocator.findHead();
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        Entity current = headLocator.findHead();
        while (current != null) {
            entities.add(current);
            Clickable clickable = current.getComponent(Clickable.class);
            RectangleComponent rect = current.getComponent(RectangleComponent.class);
            if (clickable != null && rect != null && rect.contains(mousePos.x, mousePos.y)) {
                Place place = current.getComponent(Place.class);
                if (place != null) {
                    return;
                }
            }
            current = current.getComponent(Place.class).getNext();
        }
        entities.clear();
    }

    public Entity getJustClickedPlace() {
        headLocator.findHead();
        if (Gdx.input.justTouched()) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos);

            Entity current = headLocator.findHead();
            while (current != null) {
                RectangleComponent rect = current.getComponent(RectangleComponent.class);
                Clickable clickable = current.getComponent(Clickable.class);
                if (clickable != null && rect != null && rect.contains(mousePos.x, mousePos.y)) {
                    Place place = current.getComponent(Place.class);
                    if (place != null) {
                        return current;
                    }
                }
                current = current.getComponent(Place.class).getNext();
            }
        }
        return null;
    }
}
