package com.zombie;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    public OrthoCamController (OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount < 0) {
            camera.zoom -= 0.25f;
            if (camera.zoom < 0.25) {
                camera.zoom = 0.25f;
            }
        } else {
            camera.zoom += 0.25;
            if (camera.zoom > 2) {
                camera.zoom = 2;
            }
        }

        return false;
    }
}
