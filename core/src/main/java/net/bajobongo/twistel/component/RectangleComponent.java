package net.bajobongo.twistel.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class RectangleComponent implements Component {

    private float centerX;
    private float centerY;

    private float width;
    private float height;

    public RectangleComponent() {
    }

    public RectangleComponent(float centerX, float centerY, float width, float height) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenter(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void set(float centerX, float centerY, float width, float height) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
    }



    public boolean contains(float x, float y) {
        return x >= centerX - width / 2 && x <= centerX + width / 2 && y >= centerY - height / 2 && y <= centerY + height / 2;
    }
}
