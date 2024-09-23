package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.game.ElementDisappearer;
import net.bajobongo.twistel.component.Element;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.game.HeadLocator;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.ArrayList;
import java.util.List;

public class GameplayMatchesSystem extends EntitySystem {
    private final ElementDisappearer elementDisappearer;
    private final HeadLocator headLocator;
    private final TweenService tweenService;

    @WithFactory
    public GameplayMatchesSystem(ElementDisappearer elementDisappearer, HeadLocator headLocator, TweenService tweenService) {
        this.elementDisappearer = elementDisappearer;
        this.headLocator = headLocator;
        this.tweenService = tweenService;
    }


    private List<Entity> placesToEmpty = new ArrayList<>();
    private List<Entity> running = new ArrayList<>();

    @Override
    public void update(float deltaTime) {
        if (tweenService.isTweening()) {
            return;
        }
        Entity current = headLocator.findHead();

        Element.ElementType lastType = null;
        placesToEmpty.clear();
        running.clear();

        while (current != null) {
            Place place = current.getComponent(Place.class);
            if (place != null) {
                Element.ElementType type = place.getElement() == null ? null : place.getElement().getComponent(Element.class).type;
                if (type != null) {
                    if (type.equals(lastType)) {
                        running.add(current);
                    } else {
                        if (running.size() >= 3) {
                            placesToEmpty.addAll(running);
                        }
                        running.clear();
                        running.add(current);
                        lastType = type;
                    }
                }
            } else {
                lastType = null;
                running.clear();
            }
            current = place.getNext();
        }
        if (running.size() >= 3) {
            placesToEmpty.addAll(running);
        }

        if (!placesToEmpty.isEmpty()) {
            elementDisappearer.removeElementsFromPlaces(placesToEmpty);
        }
    }
}
