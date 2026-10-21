package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class AcornPool extends Pool<Acorn> {
    private Texture acornTexture;

    public AcornPool(Texture acornTexture) {
        this.acornTexture = acornTexture;
    }

    @Override
    protected Acorn newObject() {
        return new Acorn(0,0, Config.getAcornWidth(), Config.getAcornHeight(), acornTexture);
    }
}
