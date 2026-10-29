package si.um.feri.emersic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Iterator;

public class EmersicGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture avatarTexture;
    private Texture acornTexture;
    private Texture bombTexture;
    private Texture backgroundTexture;
    private Sound avatarHurt;
    private Sound acornCollect;
    private BitmapFont font;
    private Rectangle avatar;
    private Array<Rectangle> collectibles;
    private Array<Rectangle> bombs;
    private float collectibleSpawnTime;  // in seconds
    private int acornsCollected;
    private float bombSpawnTime;  // in seconds

    private float pointSpawnTime;
    private float invincibleTime;
    private int health;

    private static final float AVATAR_SPEED = 700f;
    private static final float ACORN_SPEED = 100f;
    private static final float COLLECTIBLE_SPAWN_TIME = 4f;    // in seconds
    private static final float BOMB_SPEED = 150f;
    private static final float BOMB_DAMAGE = 25f;
    private static final float BOMB_SPAWN_TIME = 2f;  // in seconds

    private static final float POINT_SPAWN = 1f;
    private static final float INVINCIBLE_LENGTH = 3f;
    private boolean invincible = false;

    private static final float AVATAR_WIDTH = 200f;
    private static final float AVATAR_HEIGHT = 200f;

    private static final float ACORN_WIDTH = 80f;
    private static final float ACORN_HEIGHT = 80f;

    private static final float BOMB_WIDTH = 70f;
    private static final float BOMB_HEIGHT = 70f;
    private int counter = 3;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();

        avatarTexture = new Texture("images/avatar.png");
        acornTexture = new Texture("images/acorn.png");
        bombTexture = new Texture("images/bomb.png");

        backgroundTexture = new Texture("images/background.png");

        avatarHurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.mp3"));
        acornCollect = Gdx.audio.newSound(Gdx.files.internal("sounds/collect.mp3"));

        //font = new BitmapFont(Gdx.files.internal("fonts/ArcadeClassic.ttf"));
        font = new BitmapFont();

        // Naloga 2
        shapeRenderer = new ShapeRenderer();

        avatar = new Rectangle();
        avatar.x = Gdx.graphics.getWidth() / 2f - AVATAR_WIDTH / 2f;
        avatar.y = 20f;
        avatar.width = AVATAR_WIDTH;
        avatar.height = AVATAR_HEIGHT;

        collectibles = new Array<>();
        acornsCollected = 0;
        spawnCollectible();

        bombs = new Array<>();
        health = 100;
        spawnBomb();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0f, 0, 1);

        if (health > 0) {
            handleInput();
            update(Gdx.graphics.getDeltaTime());
        }

        batch.begin();

        draw();

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50, 50);
        shapeRenderer.end();
    }

    private void handleInput() {
        // A,D
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveLeft(Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveRight(Gdx.graphics.getDeltaTime());
        // Arrow Key
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveLeft(Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveRight(Gdx.graphics.getDeltaTime());
    }

    private void update(float delta) {
        float elapsedTime = (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f);
        if (elapsedTime - collectibleSpawnTime > COLLECTIBLE_SPAWN_TIME) spawnCollectible();
        if (elapsedTime - bombSpawnTime > BOMB_SPAWN_TIME) spawnBomb();
        if (elapsedTime - pointSpawnTime > POINT_SPAWN) increasePoint();
        if (elapsedTime - invincibleTime > INVINCIBLE_LENGTH) {
            invincible = false;
        } else {
            invincible = true;
        }
        if (elapsedTime - invincibleTime > POINT_SPAWN) {
            counter -= 1;
        } else {
            counter = 3;
        }

        for (Iterator<Rectangle> it = collectibles.iterator(); it.hasNext(); ) {
            Rectangle acorn = it.next();
            acorn.y -= ACORN_SPEED * delta;
            if (acorn.y + acorn.height < 0) {
                it.remove();
            }
            if (acorn.overlaps(avatar)) {
                acornsCollected++;
                acornCollect.play();
                it.remove();
            }
        }

        for (Iterator<Rectangle> it = bombs.iterator(); it.hasNext(); ) {
            Rectangle bomb = it.next();
            bomb.y -= BOMB_SPEED * delta;
            if (bomb.y + bomb.height < 0) {
                it.remove();
            }
            if (bomb.overlaps(avatar)) {
                if (invincible) {
                    it.remove();
                } else {
                    health -= BOMB_DAMAGE;
                    avatarHurt.play();
                    it.remove();
                    invincibleTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;
                }
            }
        }
    }

    private void draw() {
        if (!invincible) {
            batch.draw(
                backgroundTexture,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
            );
        } else {
            ScreenUtils.clear(0, 0f, 1, 1);
        }


        if (health <= 0) {
            font.setColor(Color.RED);
            font.draw(batch,
                "GAME OVER",
                Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f
            );
            return;
        }
        for (Rectangle acorn : collectibles) {
            batch.draw(
                acornTexture,
                acorn.x,
                acorn.y,
                acorn.width,
                acorn.height
            );
        }
        for (Rectangle bomb : bombs) {
            batch.draw(
                bombTexture,
                bomb.x,
                bomb.y,
                bomb.width,
                bomb.height
            );
        }
        batch.draw(
            avatarTexture,
            avatar.x,
            avatar.y,
            avatar.width,
            avatar.height
        );

        font.setColor(Color.GREEN);
        font.draw(batch,
            "HEALTH: " + health,
            20f, Gdx.graphics.getHeight() - 20f
        );

        font.setColor(Color.YELLOW);
        font.draw(batch,
            "ACORNS: " + acornsCollected,
            20f, Gdx.graphics.getHeight() - 60f
        );

        if (invincible) {
            font.setColor(Color.ORANGE);
            font.draw(batch, "SHIELD " + counter, 20f, 20f);
        }


        // NALOGA 2
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
        //shapeRenderer.circle(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50, 50);
        //shapeRenderer.end();

    }

    private void moveLeft(float delta) {
        avatar.x -= AVATAR_SPEED * delta;
        if (avatar.x < 0)
            avatar.x = 0f;
    }

    private void moveRight(float delta) {
        avatar.x += AVATAR_SPEED * delta;
        if (avatar.x > Gdx.graphics.getWidth() / 2f - avatar.width)
            avatar.x = Gdx.graphics.getWidth() / 2f - avatar.width;
    }

    private void spawnCollectible() {
        Rectangle acorn = new Rectangle();

        acorn.width = ACORN_WIDTH;
        acorn.height = ACORN_HEIGHT;

        acorn.x = MathUtils.random(
            0f,
            Gdx.graphics.getWidth() - acorn.width
        );

        acorn.y = Gdx.graphics.getHeight();

        collectibles.add(acorn);

        collectibleSpawnTime =
            TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;
    }

    private void increasePoint() {
        acornsCollected++;
        pointSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;
    }

    private void updateShield() {
        counter--;
    }

    private void spawnBomb() {
        Rectangle bomb = new Rectangle();


        // 1. Naloga
        bomb.width = MathUtils.random(30f, 80f);
        bomb.height = MathUtils.random(30f, 80f);

        bomb.x = MathUtils.random(
            0f,
            Gdx.graphics.getWidth() - bomb.width
        );

        bomb.y = Gdx.graphics.getHeight();

        bombs.add(bomb);

        bombSpawnTime =
            TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000f;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        avatarTexture.dispose();
        acornTexture.dispose();
        bombTexture.dispose();
        avatarHurt.dispose();
        acornCollect.dispose();
        shapeRenderer.dispose();
    }
}
