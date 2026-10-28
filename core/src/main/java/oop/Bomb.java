package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

public class Bomb extends DynamicGameObject implements Pool.Poolable {
    private PooledEffect sparks;
    public Bomb(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height, texture);

        speedY = -Config.getBombSpeed();
        speedX = 0f;
    }

    @Override
    public void reset() {
        stopSparks();
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

    public void setSparks(PooledEffect sparks) {
        this.sparks = sparks;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (sparks != null) {
            sparks.setPosition(
                rectangle.x + rectangle.width / 2f,
                rectangle.y + rectangle.height / 2f
            );
        }
    }

    public void stopSparks() {
        if (sparks != null) {
            sparks.allowCompletion();
            sparks = null;
        }
    }
}
