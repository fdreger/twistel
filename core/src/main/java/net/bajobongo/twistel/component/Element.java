package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class Element implements Component  {
    public final ElementType type;

    public Element(ElementType type) {
        this.type = type;
    }

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
}
