package oop;

public class Config {
    private static final float PLAYER_SPEED = 700f;
    private static final float ACORN_SPEED = 100f;
    private static final float COLLECTIBLE_SPAWN_TIME = 4f;    // in seconds
    private static final float BOMB_SPEED = 150f;
    private static final int BOMB_DAMAGE = 25;
    private static final float BOMB_SPAWN_TIME = 2f;  // in seconds
    private static final float PLAYER_WIDTH = 200f;
    private static final float PLAYER_HEIGHT = 200f;
    private static final float ACORN_WIDTH = 80f;
    private static final float ACORN_HEIGHT = 80f;
    private static final float BOMB_WIDTH = 70f;
    private static final float BOMB_HEIGHT = 70f;

    public static float getPlayerSpeed() {return PLAYER_SPEED;}
    public static float getAcornSpeed() {return ACORN_SPEED;}
    public static float getCollectibleSpawnTime() {return COLLECTIBLE_SPAWN_TIME;}
    public static float getBombSpeed() {return BOMB_SPEED;}
    public static int getBombDamage() {return BOMB_DAMAGE;}
    public static float getBombSpawnTime() {return BOMB_SPAWN_TIME;}
    public static float getPlayerWidth() {return PLAYER_WIDTH;}
    public static float getPlayerHeight() {return PLAYER_HEIGHT;}
    public static float getAcornWidth() {return ACORN_WIDTH;}
    public static float getAcornHeight() {return ACORN_HEIGHT;}
    public static float getBombWidth() {return BOMB_WIDTH;}
    public static float getBombHeight() {return BOMB_HEIGHT;}
}
