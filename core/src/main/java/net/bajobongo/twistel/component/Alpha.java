package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;

public class Alpha implements Component {
    public float value = 1f;

    public Alpha(float value) {
        this.value = value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
