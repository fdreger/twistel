package net.bajobongo.twistel.system;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;
import net.bajobongo.twistel.game.HeadLocator;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

public class SlideElementsSystem extends EntitySystem {


    private final TweenService tweenService;
    private final HeadLocator headLocator;

    @WithFactory
    public SlideElementsSystem(TweenService tweenService, HeadLocator headLocator) {
        this.tweenService = tweenService;
        this.headLocator = headLocator;
    }

    @Override
    public void update(float deltaTime) {
        if (tweenService.isTweening()) {
            return;
        }
        Entity currentPlaceEntity = headLocator.findHead();
        while (currentPlaceEntity != null) {
            Place place = currentPlaceEntity.getComponent(Place.class);
            if (place != null) {
                Entity elementEntity = place.getElement();
                // if element is null, try to find the next element
                if (elementEntity == null) {
                    Entity nextPlaceEntity = place.getNext();
                    if (nextPlaceEntity != null) {
                        Entity nextElement = nextPlaceEntity.getComponent(Place.class).getElement();
                        if (nextElement != null) {
                            place.setElement(nextElement);
                            nextPlaceEntity.getComponent(Place.class).setElement(null);
                            RectangleComponent target = currentPlaceEntity.getComponent(RectangleComponent.class);
                            Tween.to(nextElement.getComponent(RectangleComponent.class), 0, 0.2f)
                                .target(target.x, target.y)
                                .ease(TweenEquations.easeOutCirc)
                                .start(tweenService.getTweenManager());
                        }
                    }
                }
            }
            currentPlaceEntity = currentPlaceEntity.getComponent(Place.class).getNext();
        }
    }
}
