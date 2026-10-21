package oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

    private Array<Acorn> acorns;
    private float bombSpawnTimer = 0f;
    private float acornSpawnTimer = 0f;

    private int playerHealth = 100;
    private int playerScore = 0;

    private GameText health;
    private GameText score;
    private GameText gameOver;
    private GameText finalScore;

    @Override
    public void create() {
        this.gameAssets = new Assets();
        gameAssets.load();

        this.player = new Player(0,0, Config.getPlayerWidth(), Config.getPlayerHeight(), gameAssets.getPlayerTexture());

        this.batch = new SpriteBatch();

        this.bombs = new Array<>();
        this.acorns = new Array<>();

        this.health = new GameText(gameAssets.getFont(), "100", Color.RED,10,Gdx.graphics.getHeight()-20,5, false, false);
        this.score = new GameText(gameAssets.getFont(), "0", Color.GREEN, 0,Gdx.graphics.getHeight()-20,5, true, false);
        this.gameOver = new GameText(gameAssets.getFont(), "GAME OVER", Color.BLUE, 0, 0, 10, true, true);
        this.finalScore = new GameText(gameAssets.getFont(), "0", Color.YELLOW, 0, 200, 10, true, true);


        spawnBomb();
        spawnAcorn();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0f, 0, 1);
        float delta = Gdx.graphics.getDeltaTime();

        if (playerHealth > 0) {
            bombSpawnTimer += delta;
            if (bombSpawnTimer >= Config.getBombSpawnTime()) {
                spawnBomb();
                bombSpawnTimer = 0f;
            }

            acornSpawnTimer += delta;
            if (acornSpawnTimer >= Config.getCollectibleSpawnTime()) {
                spawnAcorn();
                acornSpawnTimer = 0f;
            }

            player.update(delta);
            for (Iterator<Bomb> it = bombs.iterator(); it.hasNext(); ) {
                Bomb bomb = it.next();
                bomb.update(delta);
                if (bomb.isBelowScreen()) {
                    it.remove();
                }
                else if (bomb.isOverlapping(player)) {
                    this.playerHealth -= Config.getBombDamage();
                    this.health.setText(String.valueOf(playerHealth));
                    it.remove();
                }
            }

            for (Iterator<Acorn> it = acorns.iterator(); it.hasNext(); ) {
                Acorn acorn = it.next();
                acorn.update(delta);
                if (acorn.isBelowScreen()) {
                    it.remove();
                }
                else if (acorn.isOverlapping(player)) {
                    this.playerScore += 1;
                    this.score.setText(String.valueOf(playerScore));
                    finalScore.setText(String.valueOf(playerScore));
                    it.remove();
                }
            }
        }

        batch.begin();
        if (playerHealth <= 0) {
            gameOver.render(batch);
            finalScore.render(batch);
        } else {
            batch.draw(gameAssets.getBackgroundTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            player.render(batch);
            for (Bomb bomb : bombs) {
                bomb.render(batch);
            }
            for (Acorn acorn : acorns) {
                acorn.render(batch);
            }
            health.render(batch);
            score.render(batch);
        }
        batch.end();

    }

    private void spawnBomb() {
        Bomb bomb = new Bomb(MathUtils.random(0f, Gdx.graphics.getWidth() - Config.getBombWidth()),Gdx.graphics.getHeight(), Config.getBombWidth(), Config.getBombHeight(), gameAssets.getBombTexture());
        bombs.add(bomb);
    }

    private void spawnAcorn() {
        Acorn acorn = new Acorn(MathUtils.random(0f, Gdx.graphics.getWidth() - Config.getAcornWidth()),Gdx.graphics.getHeight(), Config.getAcornWidth(), Config.getAcornHeight(), gameAssets.getAcornTexture());
        acorns.add(acorn);
    }

    @Override
    public void dispose() {
        gameAssets.dispose();
        batch.dispose();
    }
}
