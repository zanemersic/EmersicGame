package oop;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player extends DynamicGameObject {
    public Player(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);
    }

    @Override
    public void update(float delta) {
        speedX = 0f;
        rotationSpeed = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -Config.getPlayerSpeed();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = Config.getPlayerSpeed();
        }

        super.update(delta);

        if (rectangle.x < 0) {
            rectangle.x = 0f;
        }

        if (rectangle.x > Gdx.graphics.getWidth() - rectangle.width) {
            rectangle.x = Gdx.graphics.getWidth() - rectangle.width;
        }
    }
}
