package net.bajobongo.twistel.game;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import net.bajobongo.twistel.component.*;
import net.bajobongo.twistel.infrastructure.Ashley;
import net.bajobongo.twistel.infrastructure.TweenService;
import net.snowyhollows.bento.annotation.WithFactory;

public class InitialPattern {
    private final Ashley ashley;
    private final HeadLocator headLocator;
    private final TweenService tweenService;

    @WithFactory
    public InitialPattern(Ashley ashley, HeadLocator headLocator, TweenService tweenService) {
        this.ashley = ashley;
        this.headLocator = headLocator;
        this.tweenService = tweenService;
    }

    public void init() {
        Rectangle center = new Rectangle(0, 0, 20, 20);
        center.setCenter(400, 300);
        float angle = 160;
        float angleSpeed = 10f;
        float radiusFull = 270;


        Entity last = null;
        for (int i = 0; i < 35; i++) {
            float radius = radiusFull;
            RectangleComponent target = new RectangleComponent();
            target.set(center.x + MathUtils.cosDeg(angle) * radius, center.y + MathUtils.sinDeg(angle) * radius, 55, 55);
            Entity entity = new Entity();
            entity.add(target);
            angle = (angle + angleSpeed );
            Place place = new Place();
            if (last != null) {
                place.setNext(last);
            }
            entity.add(place);
            if (i > 21) {
                entity.add(new Clickable());
            }
            ashley.getEngine().addEntity(entity);
            last = entity;
        }
        last.add(new Head());
    }

    public void fillUp() {
        Entity last = headLocator.findHead();

        float delay = 0;
        while (true) {
            if (last.getComponent(Place.class).getElement() == null) {
                Entity elementEntity = new Entity();
                RectangleComponent rectangleComponent = new RectangleComponent();
                RectangleComponent lastRectangle = last.getComponent(RectangleComponent.class);
                rectangleComponent.setCenter(lastRectangle.getCenterX(), lastRectangle.getCenterY());
                rectangleComponent.setWidth(30);
                rectangleComponent.setHeight(30);
                elementEntity.add(rectangleComponent);
                Element element = new Element(randomType());
                Alpha alpha = new Alpha(0f);
                Tween.to(alpha, 0, 0.6f)
                    .delay(delay += 0.02f)
                    .target(1)
                    .start(tweenService.getTweenManager());
                elementEntity.add(alpha);
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
    private Element.ElementType lastType;

    private Element.ElementType randomType() {
        Element.ElementType type = randomTypeInternal();
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

    private Element.ElementType randomTypeInternal() {
        return Element.ElementType.values()[MathUtils.random(Element.ElementType.values().length - 1)];
    }
}
