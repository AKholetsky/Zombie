package com.zombie.actors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;

import javax.swing.text.View;

public class WalkingZombie extends InputAdapter {

    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private GameAnimation walkingAnimation;
    private Vector3 position;
    private float deltaTime;

    public WalkingZombie(GameAnimation walkingAnimation, SpriteBatch batch, Viewport viewport) {
        this.walkingAnimation = walkingAnimation;
        this.spriteBatch = batch;
        this.viewport = viewport;
        this.position = new Vector3();
    }

    public void create() {
        this.walkingAnimation.create();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        position = viewport.unproject(new Vector3(screenX, screenY, 0));
        return false;
    }

    public void draw(float timeStamp) {
        this.deltaTime += timeStamp;
        this.spriteBatch.draw(walkingAnimation.currentFrame(deltaTime), this.position.x, this.position.y);
    }
}
