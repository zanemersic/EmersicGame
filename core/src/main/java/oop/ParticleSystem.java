package oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ParticleSystem {
    private final ParticleEffect bombSparks;
    private final ParticleEffect acornBurst;
    private final ParticleEffectPool acornPool;
    private final Array<PooledEffect> activeBursts = new Array<>();
    private final ParticleEffectPool bombPool;
    private final Array<PooledEffect> activeSparks = new Array<>();

    public ParticleSystem() {
        this.bombSparks = new ParticleEffect();
        this.acornBurst = new ParticleEffect();

        bombSparks.load(
            Gdx.files.internal("particles/bombSparks.p"),
            Gdx.files.internal("particles")
        );

        acornBurst.load(
            Gdx.files.internal("particles/acornBurst.p"),
            Gdx.files.internal("particles")
        );

        acornPool = new ParticleEffectPool(acornBurst, 2, 20);
        bombPool = new ParticleEffectPool(bombSparks, 2, 20);
    }

    public ParticleEffect getBombSparks() {
        return bombSparks;
    }

    public ParticleEffect getAcornBurst() {
        return acornBurst;
    }

    public void spawnAcornBurst(float x, float y) {
        PooledEffect effect = acornPool.obtain();
        effect.setPosition(x, y);
        activeBursts.add(effect);
    }

    public PooledEffect spawnBombSpark(float x, float y) {
        PooledEffect effect = bombPool.obtain();
        effect.setPosition(x, y);
        activeSparks.add(effect);
        return effect;
    }

    public void update(float delta) {
        for (int i = activeBursts.size - 1; i >= 0; i--) {
            PooledEffect effect = activeBursts.get(i);
            effect.update(delta);

            if (effect.isComplete()) {
                activeBursts.removeIndex(i);
                effect.free();
            }
        }

        for (int i = activeSparks.size - 1; i >= 0; i--) {
            PooledEffect effect = activeSparks.get(i);
            effect.update(delta);

            if (effect.isComplete()) {
                activeSparks.removeIndex(i);
                effect.free();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (PooledEffect effect : activeBursts) {
            effect.draw(batch);
        }

        for (PooledEffect effect : activeSparks) {
            effect.draw(batch);
        }
    }

    public void dispose() {
        for (PooledEffect effect : activeBursts) {
            effect.free();
        }

        for (PooledEffect effect : activeSparks) {
            effect.free();
        }

        activeBursts.clear();
        acornPool.clear();

        activeSparks.clear();
        bombPool.clear();

        bombSparks.dispose();
        acornBurst.dispose();
    }
}
