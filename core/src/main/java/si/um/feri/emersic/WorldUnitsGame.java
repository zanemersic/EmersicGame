package si.um.feri.emersic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

// 1F je enako 1 svetovna enota, ena svetovna enota na razmerje 1000x800 je 100 pikslov.
public class WorldUnitsGame extends ApplicationAdapter {
    private static final float WORLD_WIDTH = 20f;
    private static final float WORLD_HEIGHT = 16f;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture backgroundTexture;
    private float playerX = 8f;
    private static final float PLAYER_SPEED = 10f;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        batch = new SpriteBatch();
        playerTexture = new Texture("images/avatar.png");
        backgroundTexture = new Texture("images/background.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        viewport.apply();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0f, 0f, WORLD_WIDTH, WORLD_HEIGHT);

        float playerWidth = 4f;
        float playerHeight = playerWidth * playerTexture.getHeight() / playerTexture.getWidth();

        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerX -= PLAYER_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerX += PLAYER_SPEED * delta;
        }

        playerX = Math.max(0f, Math.min(playerX, WORLD_WIDTH - playerWidth));

        batch.draw(playerTexture, playerX, 0.5f, playerWidth, playerHeight);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        backgroundTexture.dispose();
    }
}
