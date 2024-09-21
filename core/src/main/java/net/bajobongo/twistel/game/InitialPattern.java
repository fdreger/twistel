package net.bajobongo.twistel.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import net.bajobongo.twistel.component.*;
import net.bajobongo.twistel.infrastructure.Ashley;
import net.snowyhollows.bento.annotation.WithFactory;

public class InitialPattern {
    private final Ashley ashley;
    private final HeadLocator headLocator;

    @WithFactory
    public InitialPattern(Ashley ashley, HeadLocator headLocator) {
        this.ashley = ashley;
        this.headLocator = headLocator;
    }

    public void init() {
        Rectangle center = new Rectangle(400, 300, 20, 20);
        float angle = 0;
        float angleSpeed = 10f;
        float radiusFull = 250;
        float radiusNormalized = 1;

        Entity last = null;
        for (int i = 0; i < 35; i++) {
            float radius = radiusFull * radiusNormalized;
            RectangleComponent target = new RectangleComponent();
            target.set(center.x + MathUtils.cosDeg(angle) * radius, center.y + MathUtils.sinDeg(angle) * radius, 35 * radiusNormalized, 35 * radiusNormalized);
            Entity entity = new Entity();
            entity.add(target);
            angle = (angle + angleSpeed * (1 / radiusNormalized));
//            radiusNormalized -= 0.013;
            Place place = new Place();
            if (last != null) {
                place.setNext(last);
            }
            entity.add(place);
            ashley.getEngine().addEntity(entity);
            last = entity;
        }
        last.add(new Head());
    }

    public void fillUp() {
        Entity last = headLocator.findHead();

        while (true) {
            if (last.getComponent(Place.class).getElement() == null) {
                Entity elementEntity = new Entity();
                RectangleComponent rectangleComponent = new RectangleComponent();
                rectangleComponent.set(last.getComponent(RectangleComponent.class));
                rectangleComponent.setWidth(30);
                rectangleComponent.setHeight(30);
                elementEntity.add(rectangleComponent);
                Element element = new Element(randomType());
                last.getComponent(Place.class).setElement(elementEntity);
                elementEntity.add(element);
                ashley.getEngine().addEntity(elementEntity);
            } else {
                lastType = last.getComponent(Place.class).getElement().getComponent(Element.class).type;
                countOfSame = 2;
            }
            last = last.getComponent(Place.class).getNext();
            if (last == null) {
                break;
            }
        }

    }


    private int countOfSame;
    private ElementType lastType;
    private ElementType randomType() {
        ElementType type = randomTypeInternal();
        if (type == lastType) {
            countOfSame++;
        } else {
            countOfSame = 0;
        }
        if (countOfSame > 1) {
            return randomType();
        }
        lastType = type;
        return type;
    }

    private ElementType randomTypeInternal() {
        return ElementType.values()[MathUtils.random(ElementType.values().length - 1)];
    }
}
