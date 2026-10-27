package oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Debugger {
    private ShapeRenderer DebugRender;
    private GameText text;

    public Debugger(BitmapFont font) {
        this.DebugRender = new ShapeRenderer();
        this.text = new GameText(font, "", Color.PINK, 10f, Gdx.graphics.getHeight() - 100f, 3f, false, false);
    }

    public void drawGrid(Batch batch) {
        DebugRender.setProjectionMatrix(batch.getProjectionMatrix());
        DebugRender.begin(ShapeRenderer.ShapeType.Line);
        DebugRender.setColor(Color.GRAY);

        for (int x = 0; x <= Gdx.graphics.getWidth(); x += 50) {
            DebugRender.line(x, 0, x, Gdx.graphics.getHeight());
        }

        for (int y = 0; y <= Gdx.graphics.getHeight(); y += 50) {
            DebugRender.line(0, y, Gdx.graphics.getWidth(), y);
        }

        DebugRender.end();
    }

    public void drawBounds(Batch batch, GameObject object) {
        DebugRender.setProjectionMatrix(batch.getProjectionMatrix());
        DebugRender.begin(ShapeRenderer.ShapeType.Line);
        DebugRender.setColor(Color.YELLOW);

        DebugRender.rect(object.rectangle.x, object.rectangle.y, object.rectangle.width, object.rectangle.height);

        DebugRender.end();
    }

    public void showMemory(SpriteBatch batch) {
        float javaMB = Gdx.app.getJavaHeap() / (1024f * 1024f);
        float nativeMB = Gdx.app.getNativeHeap() / (1024f * 1024f);

        text.setText(String.format("Java: %.1f MB | Native: %.1f MB", javaMB, nativeMB));

        batch.begin();
        text.render(batch);
        batch.end();
    }

    public void dispose() {
        DebugRender.dispose();
    }
}
