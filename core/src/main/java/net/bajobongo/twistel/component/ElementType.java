package net.bajobongo.twistel.component;

import com.badlogic.gdx.graphics.Color;

public enum ElementType {
    FIRE(Color.RED),
    WATER(Color.GREEN),
    EARTH(Color.BROWN),
    AIR(Color.YELLOW);
    public final Color color;

    ElementType(Color color) {
        this.color = color;
    }
}
