package com.zombie.actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;
import com.zombie.controller.BiomController;

public class WalkingZombie extends InputAdapter {

    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final BiomController biomController;
    private GameAnimation walkingUpAnimation;
    private GameAnimation walkingDownAnimation;
    private GameAnimation stand;
    private GameAnimation cut;
    private Vector3 currPosition;
    private Vector3 moveTo;
    private float deltaTime;
    private Orientation orientation = Orientation.UP_LEFT;
    private boolean cutTheTree = false;
    private boolean walking = false;
    private Tree treeToBeCutted;

    enum Orientation {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }

    public WalkingZombie(GameAnimation walkingUpAnimation,
                         GameAnimation walkingDownAnimation,
                         GameAnimation stand,
                         GameAnimation cut,
                         SpriteBatch batch,
                         Viewport viewport,
                         BiomController biomController) {
        this.walkingUpAnimation = walkingUpAnimation;
        this.walkingDownAnimation = walkingDownAnimation;
        this.stand = stand;
        this.cut = cut;
        this.spriteBatch = batch;
        this.viewport = viewport;
        this.currPosition = new Vector3(1490, 1570, 0);
        this.moveTo = new Vector3();
        this.biomController = biomController;
    }

    public void create() {
        this.walkingUpAnimation.create();
        this.walkingDownAnimation.create();
        this.stand.create();
        this.cut.create();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        moveTo = viewport.unproject(new Vector3(screenX, screenY, 0));
        this.orientation = guessOrientation();
        Tree tree = biomController.checkTree(moveTo);
        if (tree != null) {
            cutTheTree = true;
            this.treeToBeCutted = tree;
            moveTo = new Vector3(tree.getTreePosition().x, tree.getTreePosition().y + tree.treeHeight(), 0 );
        }
        return false;
    }

    private Orientation guessOrientation() {
        if (currPosition.x < moveTo.x && currPosition.y < moveTo.y) {
            return Orientation.DOWN_RIGHT;
        } else if (currPosition.x >= moveTo.x && currPosition.y >= moveTo.y) {
            return Orientation.UP_LEFT;
        } else if (currPosition.x >= moveTo.x && currPosition.y < moveTo.y ) {
            return Orientation.DOWN_LEFT;
        } else {
            return Orientation.UP_RIGHT;
        }
    }

    public void draw(float timeStamp) {
        this.deltaTime += timeStamp;
        updatePosition();
        if (walking) {
            TextureRegion walking = guessTexture();
            this.spriteBatch.draw(
                    walking,
                    this.currPosition.x,
                    this.currPosition.y);
        } else {
            if (cutTheTree) {
                this.spriteBatch.draw(cut.currentFrame(deltaTime), this.currPosition.x, this.currPosition.y);
                treeToBeCutted.cut();
            } else {
                this.spriteBatch.draw(this.stand.currentFrame(deltaTime), this.currPosition.x, this.currPosition.y);
            }
        }
    }

    private void updatePosition() {
        if (Math.abs(currPosition.x - moveTo.x) > 1 || Math.abs(currPosition.y - moveTo.y) > 1) {
            walking = true;
            if (this.orientation == Orientation.UP_LEFT || this.orientation == Orientation.DOWN_LEFT) {
                if (currPosition.x > moveTo.x) {
                    currPosition.x -= 1;
                }
            } else {
                if (currPosition.x < moveTo.x) {
                    currPosition.x += 1;
                }
            }
            if (this.orientation == Orientation.UP_LEFT || this.orientation == Orientation.UP_RIGHT) {
                if (currPosition.y > moveTo.y) {
                    currPosition.y -= 1;
                }
            } else {
                if (currPosition.y < moveTo.y) {
                    currPosition.y += 1;
                }
            }
        } else {
            walking = false;
        }
    }

    private TextureRegion guessTexture() {
        switch (this.orientation) {
            case UP_LEFT: {
                TextureRegion textureRegion = this.walkingUpAnimation.currentFrame(deltaTime);
                if (textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                return textureRegion;
            }
            case UP_RIGHT: {
                TextureRegion textureRegion = this.walkingUpAnimation.currentFrame(deltaTime);
                if (!textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                return textureRegion;
            }
            case DOWN_RIGHT: {
                TextureRegion textureRegion = this.walkingDownAnimation.currentFrame(deltaTime);
                if (!textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                return textureRegion;
            }
            default: {
                TextureRegion textureRegion = this.walkingDownAnimation.currentFrame(deltaTime);
                if (textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                return textureRegion;
            }
        }
    }
}
