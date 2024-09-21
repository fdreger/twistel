package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import net.bajobongo.twistel.component.Element;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.RectangleComponent;
import net.bajobongo.twistel.component.SpriteService;
import net.bajobongo.twistel.infrastructure.GameCamera;
import net.bajobongo.twistel.infrastructure.GameSpriteBatch;
import net.snowyhollows.bento.annotation.WithFactory;

public class DisplaySystem extends IteratingSystem {

    private final OrthographicCamera camera;
    private final GameSpriteBatch batch;
    private final SpriteService spriteService;

    @WithFactory
    public DisplaySystem(GameCamera camera, GameSpriteBatch batch, SpriteService spriteService) {
        super(Family.all(RectangleComponent.class).get());
        this.camera = camera;
        this.spriteService = spriteService;
        this.batch = batch;
    }

    private final Vector2 tmp = new Vector2();

    @Override
    public void update(float deltaTime) {

        batch.begin();
        for (Entity entity : getEntities()) {
            Place place = entity.getComponent(Place.class);
            RectangleComponent rectangleComponent = entity.getComponent(RectangleComponent.class);
            rectangleComponent.getCenter(tmp);
            if (place != null) {
                batch.setColor(Color.WHITE);
            } else {
                batch.setColor(entity.getComponent(Element.class).type.color);
                batch.draw(spriteService.getCircle(), tmp.x - rectangleComponent.width / 2, tmp.y - rectangleComponent.height / 2, rectangleComponent.width, rectangleComponent.height);
            }
        }

        batch.end();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // intentionally left blank, the action is in the update method
    }
}
