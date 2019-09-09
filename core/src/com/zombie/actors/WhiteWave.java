package com.zombie.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.zombie.animation.GameAnimation;
import com.zombie.controller.MoveTo;

public class WhiteWave implements MoveTo {

    private final SpriteBatch sprite;
    public GameAnimation whiteWave;
    private float deltaTime = 0f;
    private Vector3 position;

    public WhiteWave(GameAnimation whiteWave, SpriteBatch sprite) {
        this.whiteWave = whiteWave;
        this.sprite = sprite;
        this.position = new Vector3();
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

    @Override
    public void moveTo(Vector3 moveToPos) {
        this.position = moveToPos;
    }
}
