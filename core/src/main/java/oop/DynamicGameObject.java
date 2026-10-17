package oop;

import com.badlogic.gdx.graphics.Texture;

public abstract class DynamicGameObject extends GameObject {
    protected float speedY;
    protected float speedX;

    protected DynamicGameObject(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);
    }

    @Override
    public void update(float delta) {
        rectangle.y += speedY * delta;
        rectangle.x = rectangle.x + speedX * delta;
    }
}
