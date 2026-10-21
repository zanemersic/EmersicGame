package oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameText {
    private BitmapFont font;
    private String text;
    private Color color;
    private float positionX;
    private float positionY;
    private float scale;
    private boolean centeredX;
    private boolean centeredY;
    private GlyphLayout layout;


    GameText(BitmapFont font, String text, Color color, float positionX, float positionY, float scale, boolean centeredX, boolean centeredY) {
        this.font = font;
        this.text = text;
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        this.scale = scale;
        this.centeredX = centeredX;
        this.centeredY = centeredY;
        this.layout = new GlyphLayout();
    }

    public void render(SpriteBatch batch) {
        font.setColor(color);
        font.getData().setScale(scale);
        layout.setText(font, text);
        if(centeredX && centeredY) {
            font.draw(batch, text, (Gdx.graphics.getWidth()/2f-layout.width/2f) + positionX, (Gdx.graphics.getHeight()/2f+layout.height/2f) - positionY);
        } else if(centeredX) {
            font.draw(batch, text, (Gdx.graphics.getWidth()/2f-layout.width/2f) + positionX, positionY);
        } else if (centeredY) {
            font.draw(batch,text, positionX, (Gdx.graphics.getHeight()/2f+layout.height/2f) - positionY);
        } else {
            font.draw(batch, text, positionX, positionY);
        }
    }

    public void setText(String text) {
        this.text = text;
    }
}
