package com.zombie.actors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.zombie.animation.GameAnimation;

public class WhiteWave extends InputAdapter {

    private final SpriteBatch sprite;
    public GameAnimation whiteWave;
    private float deltaTime = 0f;
    private float x;
    private float y;
    private final Camera camera;

    public WhiteWave(GameAnimation whiteWave, SpriteBatch sprite, Camera camera) {
        this.whiteWave = whiteWave;
        this.sprite = sprite;
        this.camera = camera;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 unproject = camera.unproject(new Vector3(screenX, screenY, 0));
        System.out.println(unproject.x + " : " + unproject.y);
        System.out.println(screenX + " : " + screenY);
        this.x = screenX;
        this.y = screenY;
        return false;
    }

    public void draw(float timeStamp) {
        this.deltaTime += timeStamp;
        this.sprite.draw(whiteWave.currentFrame(deltaTime), this.x, this.y);
    }

    public void create() {
        this.whiteWave.create();
    }
}
