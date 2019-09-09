package com.zombie.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.zombie.animation.GameAnimation;

public class Tree {

    //Single frame. Should work as single texture.
    private GameAnimation treeAnimation;
    private GameAnimation shadow;
    private GameAnimation stump;
    private SpriteBatch batch;
    private Vector3 treePosition;
    private Vector3 shadowPosition;
    private int cutPoint = 0;
    private final static int TREE_HEALTH = 8;
    private boolean cutted = false;

    public Tree(GameAnimation treeAnimation, GameAnimation shadow, GameAnimation stump, SpriteBatch batch) {
        this.treeAnimation = treeAnimation;
        this.shadow = shadow;
        this.stump = stump;
        this.batch = batch;
        //Some random position for tree
        this.treePosition = new Vector3(1570, 800, 0);
        this.shadowPosition = new Vector3(1570, 800, 0);
    }

    public void create() {
        this.treeAnimation.create();
        this.shadow.create();
        this.stump.create();
        this.shadowPosition.add(0, this.treeAnimation.config.frameHeight() / 2f, 0);
    }

    public void draw(float deltaTime) {
        if (!cutted) {
            this.batch.draw(this.shadow.currentFrame(deltaTime), shadowPosition.x, shadowPosition.y);
            this.batch.draw(this.treeAnimation.currentFrame(deltaTime), treePosition.x, treePosition.y);
        } else {
            this.batch.draw(this.stump.currentFrame(deltaTime), treePosition.x + treeWidth() / 2f, treePosition.y + treeHeight());
        }
    }

    public Vector3 getTreePosition() {
        return treePosition;
    }

    public float treeHeight() {
        return treeAnimation.config.frameHeight();
    }

    public float treeWidth() {
        return treeAnimation.config.frameWidth();
    }

    public boolean cut() {
        if (cutPoint == TREE_HEALTH) {
            cutted = true;
            return true;
        } else {
            cutPoint++;
            cutted = false;
            return false;
        }
    }

    public Vector3 centerCollision() {
        return new Vector3(
                this.treePosition.x + this.treeWidth() / 2f,
                this.treePosition.y + this.treeHeight(),
                0
        );
    }
}
