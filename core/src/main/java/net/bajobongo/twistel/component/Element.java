package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class Element implements Component  {
    public final ElementType type;

    public Element(ElementType type) {
        this.type = type;
    }

    public enum ElementType {
        FIRE(Color.RED.cpy().lerp(Color.ORANGE, 0.2f)),
        WATER(Color.BLUE.cpy().lerp(Color.CYAN, 0.2f)),
        EARTH(Color.BROWN),
        AIR(Color.CYAN.cpy().lerp(Color.WHITE, 0.9f));
        public final Color color;

        ElementType(Color color) {
            this.color = color;
        }
    }
}
