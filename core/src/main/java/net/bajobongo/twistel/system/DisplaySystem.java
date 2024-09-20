package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.bajobongo.twistel.component.Place;
import net.bajobongo.twistel.component.Element;
import net.bajobongo.twistel.component.RectangleComponent;

public class DisplaySystem extends IteratingSystem {

    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;

    public DisplaySystem(OrthographicCamera camera) {
        super(Family.all(RectangleComponent.class).get());
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
    }

    private final Vector2 tmp = new Vector2();

    @Override
    public void update(float deltaTime) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Entity entity : getEntities()) {
            Place place = entity.getComponent(Place.class);
            RectangleComponent rectangleComponent = entity.getComponent(RectangleComponent.class);
            rectangleComponent.getCenter(tmp);
            if (place != null) {
                if (shapeRenderer.getCurrentType() != ShapeRenderer.ShapeType.Line) {
                    shapeRenderer.end();
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                }
                shapeRenderer.circle(tmp.x, tmp.y, rectangleComponent.width);
            } else {
                if (shapeRenderer.getCurrentType() != ShapeRenderer.ShapeType.Filled) {
                    shapeRenderer.end();
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                }
                shapeRenderer.setColor(entity.getComponent(Element.class).type.color);
                shapeRenderer.circle(tmp.x, tmp.y, rectangleComponent.width);
                shapeRenderer.setColor(Color.WHITE);
            }
        }

        shapeRenderer.end();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
