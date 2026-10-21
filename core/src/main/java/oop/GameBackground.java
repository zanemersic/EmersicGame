package oop;

import com.badlogic.gdx.graphics.Texture;

public class GameBackground extends GameObject {

    public GameBackground(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);
    }

    @Override
    public void update(float delta) {}
}
