package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class DynamicGameObject extends GameObject {
    protected float speedY;
    protected float speedX;

    protected float rotation;
    protected float rotationSpeed;

    protected DynamicGameObject(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);
    }

    @Override
    public void update(float delta) {
        rectangle.y += speedY * delta;
        rectangle.x = rectangle.x + speedX * delta;
        rotation = rotation + rotationSpeed * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Origin is the point of rotation
       batch.draw(texture, rectangle.x, rectangle.y, rectangle.width/2, rectangle.height/2, rectangle.width, rectangle.height, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
