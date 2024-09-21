package net.bajobongo.twistel.game;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Entity;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.infrastructure.Ashley;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.List;

public class ElementDisappearer {

    private final Ashley ashley;
    private final GameStateService gameStateService;
    private final TweenService tweenService;

    @WithFactory
    public ElementDisappearer(Ashley ashley, GameStateService gameStateService, TweenService tweenService) {
        this.ashley = ashley;
        this.gameStateService = gameStateService;
        this.tweenService = tweenService;
    }

    public void removeElementsFromPlaces(List<Entity> placesToEmpty) {
        int bonus = 0;
        switch (placesToEmpty.size()) {
            case 1:
            case 2:
                break;
            case 3:
                bonus = 10;
                break;
            case 4:
                bonus = 20;
                break;
            default:
                bonus = (20 + (placesToEmpty.size() - 4) * 10);
                break;
        }

        int totalBonus = bonus;
        for (Entity placeEntity : placesToEmpty) {
            Place place = placeEntity.getComponent(Place.class);
            Entity toRemove = place.getElement();
            place.setElement(null);
            Tween.mark().delay(0.6f).setCallback((type, source) -> {
                ashley.getEngine().removeEntity(toRemove);
            }).start(tweenService.getTweenManager());
            Tween.mark().delay(0.6f).setCallback((type, source) -> {
                gameStateService.addScore(totalBonus);
            }).start(tweenService.getTweenManager());
        }
    }
}
