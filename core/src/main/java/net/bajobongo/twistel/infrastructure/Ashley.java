package net.bajobongo.twistel.infrastructure;

import com.badlogic.ashley.core.Engine;
import net.snowyhollows.bento.annotation.WithFactory;

public class Ashley {

    private Engine engine = new Engine();

    @WithFactory
    public Ashley() {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

}
