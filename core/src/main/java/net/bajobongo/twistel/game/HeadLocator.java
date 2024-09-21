package net.bajobongo.twistel.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import net.bajobongo.twistel.infrastructure.Ashley;
import net.bajobongo.twistel.component.Head;
import net.snowyhollows.bento.annotation.WithFactory;


public class HeadLocator {

    private final Ashley ashley;
    private Family family = Family.all(Head.class).get();

    @WithFactory
    public HeadLocator(Ashley ashley) {
        this.ashley = ashley;
    }

    public Entity findHead() {
        return ashley.getEngine().getEntitiesFor(family).first();
    }

}
