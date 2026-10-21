package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class Bomb extends DynamicGameObject implements Pool.Poolable {
    public Bomb(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);

        speedY = -Config.getBombSpeed();
        speedX = 0f;
    }

    @Override
    public void reset() {
        rectangle.x = 0f;
        rectangle.y = 0f;
        speedX = 0f;
        speedY = 0f;
        rotationSpeed = 0f;
        rotation = 0f;
    }

    public void init(float x, float y) {
        rectangle.x = x;
        rectangle.y = y;
        speedX = 0f;
        speedY = -Config.getBombSpeed();
        rotationSpeed = 90f;
        rotation = 0f;
    }
}
