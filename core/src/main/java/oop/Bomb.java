package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bomb extends DynamicGameObject {
    public Bomb(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);

        speedY = -150f;
        speedX = 0f;
    }

}
