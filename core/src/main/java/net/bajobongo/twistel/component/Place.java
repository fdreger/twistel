package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class Place implements Component {
    private Entity next;
    private Entity element;

    public Entity getElement() {
        return element;
    }

    public void setElement(Entity element) {
        this.element = element;
    }


    public Entity getNext() {
        return next;
    }

    public void setNext(Entity next) {
        this.next = next;
    }
}
