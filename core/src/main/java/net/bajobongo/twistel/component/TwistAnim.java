package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class TwistAnim implements Component {

    float ox, oy;
    float r;
    float a, da;
    float totalTime;

    private float t = 0;

    public TwistAnim(float ox, float oy, float r, float a, float da, float totalTime) {
        this.ox = ox;
        this.oy = oy;
        this.r = r;
        this.a = a;
        this.da = da;
        this.totalTime = totalTime;
    }

    public void update(float deltaTime) {
        t += deltaTime;
    }

    public void calculate(Rectangle position) {
        float ratio = Math.min(t / totalTime, 1);

        float currentArc = (a + da * ratio + MathUtils.PI2) % MathUtils.PI2;

        float dx = MathUtils.cos(currentArc) * r;
        float dy = MathUtils.sin(currentArc) * r;

        position.setCenter(ox + dx, oy+ dy);
    }

    public boolean isFinished() {
        return t > totalTime;
    }
}
