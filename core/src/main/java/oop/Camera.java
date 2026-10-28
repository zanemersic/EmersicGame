package oop;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Camera {
    private OrthographicCamera camera;

    public Camera(float width, float height) {
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
    }

    public void updateDebugCamera(float delta, String update) {
        float speed = 500f * delta * camera.zoom;

        switch (update) {
            case "UP":
                camera.position.y += speed;
                break;
            case "DOWN":
                camera.position.y -= speed;
                break;
            case "LEFT":
                camera.position.x -= speed;
                break;
            case "RIGHT":
                camera.position.x += speed;
                break;
            case "ZOOMIN":
                camera.zoom -= delta;
                break;
            case "ZOOMOUT":
                camera.zoom += delta;
                break;
            case "RESET":
                resetCamera();
                break;
        }

        if (camera.zoom < 0.2f) {
            camera.zoom = 0.2f;
        }

        if (camera.zoom > 3f) {
            camera.zoom = 3f;
        }

    }

    public void resetCamera() {
        camera.position.set(camera.viewportWidth / 2f,camera.viewportHeight / 2f,0f);
        camera.zoom = 1f;
    }

    public void update() {
        camera.update();
    }

    public Matrix4 getCombined() {
        return camera.combined;
    }
}
