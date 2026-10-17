package oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Iterator;

public class Game extends ApplicationAdapter {
    private Assets gameAssets;
    private Player player;
    private SpriteBatch batch;
    private Array<Bomb> bombs;
    private float bombSpawnTimer = 0f;

    @Override
    public void create() {
        this.gameAssets = new Assets();
        gameAssets.load();

        this.player = new Player(0,0, Config.getPlayerWidth(), Config.getPlayerHeight(), gameAssets.getPlayerTexture());

        this.batch = new SpriteBatch();

        this.bombs = new Array<>();

        spawnBomb();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0f, 0, 1);
        float delta = Gdx.graphics.getDeltaTime();

        bombSpawnTimer += delta;
        if (bombSpawnTimer >= Config.getBombSpawnTime()) {
            spawnBomb();
            bombSpawnTimer = 0f;
        }

        player.update(delta);
        for (Iterator<Bomb> it = bombs.iterator(); it.hasNext(); ) {
            Bomb bomb = it.next();
            bomb.update(delta);
            if (bomb.isBelowScreen()) {
                it.remove();
            }
        }

        batch.begin();
        player.render(batch);
        for (Bomb bomb: bombs) {
            bomb.render(batch);
        }
        batch.end();

    }

    private void spawnBomb() {
        Bomb bomb = new Bomb(MathUtils.random(0f, Gdx.graphics.getWidth() - Config.getBombWidth()),Gdx.graphics.getHeight(), Config.getBombWidth(), Config.getBombHeight(), gameAssets.getBombTexture());
        bombs.add(bomb);
    }

    @Override
    public void dispose() {
        gameAssets.dispose();
        batch.dispose();
    }
}
