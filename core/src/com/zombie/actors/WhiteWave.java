package com.zombie.actors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;

public class WhiteWave extends InputAdapter {

    private final SpriteBatch sprite;
    public GameAnimation whiteWave;
    private float deltaTime = 0f;
    private float x;
    private float y;
    private final Viewport viewport;

    public WhiteWave(GameAnimation whiteWave, SpriteBatch sprite, Viewport viewport) {
        this.whiteWave = whiteWave;
        this.sprite = sprite;
        this.viewport = viewport;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 unproject = viewport.unproject(new Vector3(screenX, screenY, 0));
        System.out.println(unproject.x + " : " + unproject.y);
        System.out.println(screenX + " : " + screenY);
        this.x = unproject.x;
        this.y = unproject.y;
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
