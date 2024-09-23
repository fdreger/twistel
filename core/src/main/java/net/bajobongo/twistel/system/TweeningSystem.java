package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.EntitySystem;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

public class TweeningSystem extends EntitySystem {
    private final TweenService tweenService;

    @WithFactory
    public TweeningSystem(TweenService tweenService) {
        this.tweenService = tweenService;
    }

    @Override
    public void update(float deltaTime) {
        tweenService.getFxTweenManager().update(deltaTime);
        tweenService.getTweenManager().update(deltaTime);
    }

}
