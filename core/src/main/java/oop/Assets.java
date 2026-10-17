package oop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    private Texture bombTexture;
    private Texture acornTexture;
    private Texture playerTexture;
    private Texture backgroundTexture;

    private Sound avatarHurt;
    private Sound acornCollect;

    public void load() {
        playerTexture = new Texture("images/avatar.png");
        acornTexture = new Texture("images/acorn.png");
        bombTexture = new Texture("images/bomb.png");
        backgroundTexture = new Texture("images/background.png");

        avatarHurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.mp3"));
        acornCollect = Gdx.audio.newSound(Gdx.files.internal("sounds/collect.mp3"));
    }

    public Texture getBombTexture() {
        return bombTexture;
    }

    public Texture getAcornTexture() {
        return acornTexture;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Sound getAvatarHurt() {
        return avatarHurt;
    }

    public Sound getAcornCollect() {
        return acornCollect;
    }

    public void dispose() {
        playerTexture.dispose();
        acornTexture.dispose();
        bombTexture.dispose();
        backgroundTexture.dispose();
        avatarHurt.dispose();
        acornCollect.dispose();
    }
}
