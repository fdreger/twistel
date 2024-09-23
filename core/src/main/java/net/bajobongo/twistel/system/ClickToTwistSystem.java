package net.bajobongo.twistel.system;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import net.bajobongo.twistel.assets.AssetsService;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;
import net.bajobongo.twistel.game.GameStateService;
import net.bajobongo.twistel.game.HeadLocator;
import net.bajobongo.twistel.game.PlayerInput;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.ArrayList;
import java.util.List;

public class ClickToTwistSystem extends EntitySystem {
    private final GameStateService gameStateService;
    private final PlayerInput playerInput;
    private final HeadLocator headLocator;
    private final TweenService tweenService;
    private final AssetsService assetsService;

    @WithFactory
    public ClickToTwistSystem(GameStateService gameStateService, PlayerInput playerInput, HeadLocator headLocator, TweenService tweenService, AssetsService assetsService) {
        this.gameStateService = gameStateService;
        this.playerInput = playerInput;
        this.headLocator = headLocator;
        this.tweenService = tweenService;
        this.assetsService = assetsService;
    }

    private List<Entity> placesToTwist = new ArrayList<>();

    @Override
    public void update(float deltaTime) {
        if (tweenService.isTweening() || !gameStateService.isPlaying()) {
            return;
        }
        Entity placeEntity = playerInput.getJustClickedPlace();
        if (placeEntity == null) return;

        Entity head = headLocator.findHead();
        Entity current = head;
        placesToTwist.clear();
        while (current != null) {
            placesToTwist.add(current);
            if (current == placeEntity) break;
            current = current.getComponent(Place.class).getNext();
        }

        int pairs = (int) Math.ceil(placesToTwist.size() / 2.0);
        if (pairs > 0) {
            assetsService.getWoosh().play(0.2f, (float) (1 + Math.random() * 0.2f), 0);
        }
        for (int i = 0; i < pairs; i++) {
            Entity place1 = placesToTwist.get(i);
            Entity place2 = placesToTwist.get(placesToTwist.size() - i - 1);
            Entity element1 = place1.getComponent(Place.class).getElement();
            Entity element2 = place2.getComponent(Place.class).getElement();
            place1.getComponent(Place.class).setElement(element2);
            place2.getComponent(Place.class).setElement(element1);
            tweenFromTo(element1, element2);
            tweenFromTo(element2, element1);
        }
    }

    void tweenFromTo(Entity from, Entity to) {
        if (from == null || to == null) return;
        tweenFromTo(from.getComponent(RectangleComponent.class), to.getComponent(RectangleComponent.class));
    }

    private Vector2 tmpFrom = new Vector2();
    private Vector2 tmpTo = new Vector2();
    private Vector2 middle = new Vector2();
    private Vector2 unit = new Vector2();
    void tweenFromTo(RectangleComponent from, RectangleComponent to) {

        tmpFrom = new Vector2(from.getCenterX(), from.getCenterY());
        tmpTo = new Vector2(to.getCenterX(), to.getCenterY());
        float length = tmpFrom.dst(tmpTo);

        middle.set(tmpFrom.cpy()).add(tmpTo).scl(0.5f);

        unit.set(tmpTo).sub(tmpFrom).nor().rotate90(1).scl(length / 2);

        Tween.to(from, 0, 0.3f)
            .target(to.getCenterX(), to.getCenterY())
            .waypoint(middle.x + unit.x, middle.y + unit.y)
            .ease(TweenEquations.easeInOutQuad)
            .start(tweenService.getTweenManager());
    }
}
