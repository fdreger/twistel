package net.bajobongo.twistel.infrastructure;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import net.bajobongo.twistel.component.RectangleComponent;
import net.snowyhollows.bento.annotation.WithFactory;

public class TweenService {
    private final TweenManager tweenManager;
    private final TweenManager fxTweenManager;

    @WithFactory
    public TweenService() {
        tweenManager = new TweenManager();
        fxTweenManager = new TweenManager();

        Tween.setWaypointsLimit(8);
        Tween.registerAccessor(RectangleComponent.class, new TweenAccessor<RectangleComponent>() {
            @Override
            public int getValues(RectangleComponent target, int tweenType, float[] returnValues) {
                returnValues[0] = target.x;
                returnValues[1] = target.y;
                return 2;
            }

            @Override
            public void setValues(RectangleComponent target, int tweenType, float[] newValues) {
                target.x = newValues[0];
                target.y = newValues[1];
            }
        });

    }

    public boolean isTweening() {
        return tweenManager.getRunningTweensCount() != 0 || tweenManager.getRunningTimelinesCount() != 0;
    }

    public TweenManager getTweenManager() {
        return tweenManager;
    }

    public TweenManager getFxTweenManager() {
        return fxTweenManager;
    }
}
