package net.bajobongo.twistel.component;

import com.badlogic.gdx.math.Rectangle;

public class SlideAnim {
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;
    private float duration;
    private float time;

    public SlideAnim(float fromX, float fromY, float toX, float toY, float duration) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.duration = duration;
        this.time = 0;
    }

    public void update(float deltaTime) {
        time += deltaTime;
    }

    public void calculate(Rectangle position) {
        float ratio = Math.min(time / duration, 1);
        position.x = fromX + (toX - fromX) * ratio;
        position.y = fromY + (toY - fromY) * ratio;
    }

    public boolean isFinished() {
        return time > duration;
    }
}
