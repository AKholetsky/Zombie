package com.zombie;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.controller.Dragged;
import com.zombie.controller.Scrolled;
import com.zombie.controller.TouchUp;

public class OrthoCamController implements Dragged, Scrolled, TouchUp {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();
    private final Viewport viewport;

    public OrthoCamController (OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    @Override
    public void dragged (int x, int y) {
        viewport.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            viewport.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
    }

    @Override
    public void touchUp (int x, int y) {
        last.set(-1, -1, -1);
    }

    @Override
    public void scrolled(int amount) {
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

    }
}
