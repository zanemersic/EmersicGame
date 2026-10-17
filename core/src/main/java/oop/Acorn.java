package oop;

import com.badlogic.gdx.graphics.Texture;

public class Acorn extends DynamicGameObject {
    public Acorn(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);

        speedY = -Config.getAcornSpeed();
        speedX = 0f;
    }

}
