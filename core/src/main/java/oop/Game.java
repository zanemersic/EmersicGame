package oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
    private Assets gameAssets;
    private Player player;
    private SpriteBatch batch;

    @Override
    public void create() {
        this.gameAssets = new Assets();
        gameAssets.load();

        this.player = new Player(0,0, Config.getPlayerWidth(), Config.getPlayerHeight(), gameAssets.getPlayerTexture());

        this.batch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0f, 0, 1);
        player.update(Gdx.graphics.getDeltaTime());

        batch.begin();
        player.render(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        gameAssets.dispose();
        batch.dispose();
    }
}
