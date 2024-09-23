package net.bajobongo.twistel.game;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import net.bajobongo.twistel.assets.AssetsService;
import net.bajobongo.twistel.component.Alpha;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;
import net.bajobongo.twistel.infrastructure.Ashley;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class ElementDisappearer {

    private final Ashley ashley;
    private final GameStateService gameStateService;
    private final TweenService tweenService;
    private final AssetsService assetsService;

    @WithFactory
    public ElementDisappearer(Ashley ashley, GameStateService gameStateService, TweenService tweenService, AssetsService assetsService) {
        this.ashley = ashley;
        this.gameStateService = gameStateService;
        this.tweenService = tweenService;
        this.assetsService = assetsService;
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
            Alpha alpha = toRemove.getComponent(Alpha.class);
            RectangleComponent rectangleComponent = toRemove.getComponent(RectangleComponent.class);
            Tween.to(alpha, 0, random(0.4f, 1f))
                .target(0)
                .start(tweenService.getFxTweenManager());
            Tween.to(rectangleComponent, 0, random(0.5f,1f))
                .target(400, 300)
                .setCallback((type, source) -> {
                    ashley.getEngine().removeEntity(toRemove);
                    assetsService.getPlink().play(random(0.1f,0.3f), random(1f, 1.2f), 0);
                })
                .start(tweenService.getFxTweenManager());
            Tween.mark().delay(0.6f).setCallback((type, source) -> {
                gameStateService.addScore(totalBonus);
            }).start(tweenService.getFxTweenManager());
        }
    }
}
