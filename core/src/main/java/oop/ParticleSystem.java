package oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ParticleSystem {
    private final ParticleEffect bombSparks;
    private final ParticleEffect acornBurst;

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
    }

    public ParticleEffect getBombSparks() {
        return bombSparks;
    }

    public ParticleEffect getAcornBurst() {
        return acornBurst;
    }

    public void dispose() {
        bombSparks.dispose();
        acornBurst.dispose();
    }
}
