package com.zombie.actors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;

public class WhiteWave extends InputAdapter {

    private final SpriteBatch sprite;
    public GameAnimation whiteWave;
    private float deltaTime = 0f;
    private Vector3 position;
    private final Viewport viewport;

    public WhiteWave(GameAnimation whiteWave, SpriteBatch sprite, Viewport viewport) {
        this.whiteWave = whiteWave;
        this.sprite = sprite;
        this.viewport = viewport;
        this.position = new Vector3();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.position = viewport.unproject(new Vector3(screenX, screenY, 0));
        return false;
    }

    public void draw(float timeStamp) {
        this.deltaTime += timeStamp;
        final Vector3 centeredPos = whiteWave.centerPosition(this.position);
        this.sprite.draw(
                whiteWave.currentFrame(deltaTime),
                centeredPos.x,
                centeredPos.y);
    }

    public void create() {
        this.whiteWave.create();
    }
}
