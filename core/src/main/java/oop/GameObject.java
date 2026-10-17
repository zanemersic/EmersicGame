package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected Rectangle rectangle;
    protected Texture texture;

    protected GameObject(float x, float y, float width, float height, Texture texture) {
        rectangle = new Rectangle(x, y, width, height);
        this.texture = texture;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
