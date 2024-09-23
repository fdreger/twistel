package net.bajobongo.twistel.infrastructure;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.math.Vector2;
import net.bajobongo.twistel.component.Alpha;
import net.bajobongo.twistel.component.RectangleComponent;
import net.snowyhollows.bento.annotation.WithFactory;

public class TweenService {
    private final TweenManager tweenManager;
    private final TweenManager fxTweenManager;
    private final Vector2 tmp = new Vector2();

    @WithFactory
    public TweenService() {
        tweenManager = new TweenManager();
        fxTweenManager = new TweenManager();

        Tween.setWaypointsLimit(2);

        Tween.registerAccessor(Alpha.class, new TweenAccessor<Alpha>() {
            @Override
            public int getValues(Alpha target, int tweenType, float[] returnValues) {
                returnValues[0] = target.value;
                return 1;
            }

            @Override
            public void setValues(Alpha target, int tweenType, float[] newValues) {
                target.value = newValues[0];
            }
        });
        Tween.registerAccessor(RectangleComponent.class, new TweenAccessor<RectangleComponent>() {
            @Override
            public int getValues(RectangleComponent target, int tweenType, float[] returnValues) {
                returnValues[0] = target.getCenterX();
                returnValues[1] = target.getCenterY();
                return 2;
            }

            @Override
            public void setValues(RectangleComponent target, int tweenType, float[] newValues) {
                target.setCenter(newValues[0], newValues[1]);
            }
        });

    }

    public boolean isTweening() {
        return tweenManager.getRunningTweensCount() != 0 || tweenManager.getRunningTimelinesCount() != 0;
    }

    public boolean isAnythingTweening() {
        return tweenManager.getRunningTweensCount() != 0 || tweenManager.getRunningTimelinesCount() != 0 || fxTweenManager.getRunningTweensCount() != 0 || fxTweenManager.getRunningTimelinesCount() != 0;
    }

    public TweenManager getTweenManager() {
        return tweenManager;
    }

    public TweenManager getFxTweenManager() {
        return fxTweenManager;
    }
}
