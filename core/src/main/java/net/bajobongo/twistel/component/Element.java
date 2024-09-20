package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;

public class Element implements Component  {
    public final ElementType type;

    public Element(ElementType type) {
        this.type = type;
    }
}
