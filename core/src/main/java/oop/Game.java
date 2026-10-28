package oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;

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

    private GameBackground background;

    private GameText health;
    private GameText score;
    private GameText gameOver;
    private GameText finalScore;
    private BombPool bombPool;
    private AcornPool acornPool;
    private boolean debug = false;
    private Debugger debugger;
    private Camera camera;
    private ParticleSystem particleSystem;

    @Override
    public void create() {
        this.gameAssets = new Assets();
        gameAssets.load();

        this.background = new GameBackground(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),gameAssets.getBackgroundTexture());

        this.bombPool = new BombPool(gameAssets.getBombTexture());
        this.acornPool = new AcornPool(gameAssets.getAcornTexture());

        this.player = new Player(0,0, Config.getPlayerWidth(), Config.getPlayerHeight(), gameAssets.getPlayerTexture());


        this.batch = new SpriteBatch();

        this.bombs = new Array<>();
        this.acorns = new Array<>();

        this.debugger = new Debugger(gameAssets.getFont());

        this.health = new GameText(gameAssets.getFont(), "100", Color.RED,10,Gdx.graphics.getHeight()-20,5, false, false);
        this.score = new GameText(gameAssets.getFont(), "0", Color.GREEN, 0,Gdx.graphics.getHeight()-20,5, true, false);
        this.gameOver = new GameText(gameAssets.getFont(), "GAME OVER", Color.BLUE, 0, 0, 10, true, true);
        this.finalScore = new GameText(gameAssets.getFont(), "0", Color.YELLOW, 0, 200, 10, true, true);

        this.camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.particleSystem = new ParticleSystem();

        spawnBomb();
        spawnAcorn();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0f, 0, 1);
        float delta = Gdx.graphics.getDeltaTime();

        // TU BI MOGLA BIT TIPKA F1 SAMO MENI LINUX PAC NE PUSTI MET F1... :/
        if ((Gdx.input.isKeyJustPressed(Input.Keys.G) || Gdx.input.isKeyJustPressed(Input.Keys.F1)) && !debug) {
            debug = true;
            System.out.println("Debug ON");
        } else if ((Gdx.input.isKeyJustPressed(Input.Keys.G) || Gdx.input.isKeyJustPressed(Input.Keys.F1)) && debug) {
            debug = false;
            System.out.println("Debug OFF");
        }

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
                    bombPool.free(bomb);
                }
                else if (bomb.isOverlapping(player)) {
                    this.playerHealth -= Config.getBombDamage();
                    this.health.setText(String.valueOf(playerHealth));
                    gameAssets.getAvatarHurt().play();
                    it.remove();
                    bombPool.free(bomb);
                }
            }

            for (Iterator<Acorn> it = acorns.iterator(); it.hasNext(); ) {
                Acorn acorn = it.next();
                acorn.update(delta);

                if (acorn.isBelowScreen()) {
                    it.remove();
                    acornPool.free(acorn);
                } else if (acorn.isOverlapping(player)) {
                    playerScore++;
                    score.setText(String.valueOf(playerScore));
                    finalScore.setText(String.valueOf(playerScore));
                    gameAssets.getAcornCollect().play();

                    particleSystem.spawnAcornBurst(
                        acorn.rectangle.x + acorn.rectangle.width / 2f,
                        acorn.rectangle.y + acorn.rectangle.height / 2f
                    );

                    it.remove();
                    acornPool.free(acorn);
                }
            }
        }

        if (debug) {
            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                camera.updateDebugCamera(delta, "UP");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                camera.updateDebugCamera(delta, "DOWN");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                camera.updateDebugCamera(delta, "LEFT");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                camera.updateDebugCamera(delta, "RIGHT");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                camera.updateDebugCamera(delta, "ZOOMIN");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                camera.updateDebugCamera(delta, "ZOOMOUT");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                camera.resetCamera();
            }
        } else {
            camera.resetCamera();
        }

        if (playerHealth <= 0) {
            for (Bomb bomb : bombs) {
                bomb.stopSparks();
            }
        }

        particleSystem.update(delta);
        camera.update();
        batch.setProjectionMatrix(camera.getCombined());

        batch.begin();
        if (playerHealth <= 0) {
            gameOver.render(batch);
            finalScore.render(batch);
        } else {
            background.render(batch);
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
        particleSystem.render(batch);
        batch.end();

        if (debug) {
            debugger.drawGrid(batch);
            debugger.showMemory(batch);
            //debugger.drawCircle(batch, 300f, 200f, 50f);

            if (playerHealth > 0) {
                debugger.drawBounds(batch, background);
                debugger.drawBounds(batch, player);
            }

            for (Bomb bomb : bombs) {
                debugger.drawBounds(batch, bomb);
            }

            for (Acorn acorn : acorns) {
                debugger.drawBounds(batch, acorn);
            }
        }

    }

    private void spawnBomb() {
        Bomb bomb = bombPool.obtain();
        bomb.init(MathUtils.random(0f, Gdx.graphics.getWidth() - Config.getBombWidth()), Gdx.graphics.getHeight());
        bomb.setSparks(
            particleSystem.spawnBombSpark(
                bomb.rectangle.x + bomb.rectangle.width / 2f,
                bomb.rectangle.y + bomb.rectangle.height / 2f
            )
        );
        bombs.add(bomb);
    }

    private void spawnAcorn() {
        Acorn acorn = acornPool.obtain();
        acorn.init(MathUtils.random(0f, Gdx.graphics.getWidth() - Config.getAcornWidth()),Gdx.graphics.getHeight());
        acorns.add(acorn);
    }

    @Override
    public void dispose() {
        gameAssets.dispose();
        batch.dispose();
        debugger.dispose();
        particleSystem.dispose();
    }
}
