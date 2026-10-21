package oop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

public class BombPool extends Pool<Bomb> {
    private Texture bombTexture;

    public BombPool(Texture bombTexture) {
        this.bombTexture = bombTexture;
    }

    @Override
    protected Bomb newObject() {
        return new Bomb(0,0, Config.getBombWidth(), Config.getBombHeight(), bombTexture);
    }
}
