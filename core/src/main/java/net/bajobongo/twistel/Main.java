package net.bajobongo.twistel;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import net.bajobongo.twistel.component.*;
import net.bajobongo.twistel.system.ClickToRemoveSystem;
import net.bajobongo.twistel.system.DisplaySystem;
import net.bajobongo.twistel.system.MoveElementsSystem;
import net.bajobongo.twistel.system.SlideElementsSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Engine engine;
    private OrthographicCamera camera;

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        engine = new Engine();
        Rectangle center = new Rectangle(0, 0, 20, 20);
        float angle = 0;
        float angleSpeed = 10f;
        float radius = 200;

        Entity last = null;
        for (int i = 0; i < 30; i++) {
            RectangleComponent target = new RectangleComponent();
            target.set(center.x + MathUtils.cosDeg(angle) * radius, center.y + MathUtils.sinDeg(angle) * radius, 20, 20);
            Entity entity = new Entity();
            entity.add(target);
            angle = (angle + angleSpeed);
            Place place = new Place();
            if (last != null) {
                place.setNext(last);
            }
            entity.add(place);
            engine.addEntity(entity);
            last = entity;
        }
        last.add(new Head());
        engine.addSystem(new MoveElementsSystem(last));
        engine.addSystem(new SlideElementsSystem(last));
        engine.addSystem(new ClickToRemoveSystem(last, camera));

        while (true) {
            Entity elementEntity = new Entity();
            RectangleComponent rectangleComponent = new RectangleComponent();
            rectangleComponent.setWidth(10);
            elementEntity.add(rectangleComponent);
            Element element = new Element(randomType());
            last.getComponent(Place.class).setElement(elementEntity);
            elementEntity.add(element);
            engine.addEntity(elementEntity);
            last = last.getComponent(Place.class).getNext();
            if (last == null) {
                break;
            }
        }

        engine.addSystem(new DisplaySystem(camera));

    }

    private ElementType randomType() {
        return ElementType.values()[MathUtils.random(ElementType.values().length - 1)];
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {

    }
}
