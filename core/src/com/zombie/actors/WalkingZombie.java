package com.zombie.actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;
import com.zombie.controller.BiomController;
import com.zombie.controller.MoveAndCut;
import com.zombie.controller.MoveTo;

public class WalkingZombie implements MoveAndCut {

    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final BiomController biomController;
    private GameAnimation walkingUpAnimation;
    private GameAnimation walkingDownAnimation;
    private GameAnimation stand;
    private GameAnimation cut;
    private Vector3 currPosition;
    private Vector3 moveToPoint;
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
        this.moveToPoint = new Vector3();
        this.biomController = biomController;
    }

    public void create() {
        this.walkingUpAnimation.create();
        this.walkingDownAnimation.create();
        this.stand.create();
        this.cut.create();
    }

    //Point at left down corner of sprite
    private Vector3 leftCollision() {
        return new Vector3(
                this.currPosition.x,
                this.currPosition.y + this.walkingUpAnimation.config.frameHeight(),
                0
        );
    }

    //Point at right down corner of sprite
    private Vector3 rightCollision() {
        return new Vector3(
                this.currPosition.x + this.walkingUpAnimation.config.frameWidth(),
                this.currPosition.y + this.walkingUpAnimation.config.frameHeight(),
                0
        );
    }

    @Override
    public void moveTo(Vector3 moveToPos) {
        moveToPoint = moveToPos;
    }

    @Override
    public void moveAndCut(Tree tree) {
        if (leftCollision().x < tree.leftCOllision().x) {
            moveToPoint = tree.leftCOllision();
        } else {
            moveToPoint = tree.rightCollision();
        }

    }

    private Orientation guessOrientation() {
        if (moveToPoint.x <= leftCollision().x && moveToPoint.y <= leftCollision().y) {
            return Orientation.UP_LEFT;
        } else if (moveToPoint.x > leftCollision().x && moveToPoint.y <= leftCollision().y) {
            return Orientation.UP_RIGHT;
        } else if (moveToPoint.x <= leftCollision().x && moveToPoint.y > leftCollision().y) {
            return Orientation.DOWN_LEFT;
        } else {
            return Orientation.DOWN_RIGHT;
        }
    }

    public void draw(float timeStamp) {
        this.deltaTime += timeStamp;
        this.orientation = guessOrientation();
        updatePosition();
//        if (walking) {
            TextureRegion walking = guessTexture();
            this.spriteBatch.draw(
                    walking,
                    this.currPosition.x,
                    this.currPosition.y);
//        } else {
//            if (cutTheTree) {
//                this.spriteBatch.draw(cut.currentFrame(deltaTime), this.currPosition.x, this.currPosition.y);
//                treeToBeCutted.cut();
//            } else {
//                this.spriteBatch.draw(this.stand.currentFrame(deltaTime), this.currPosition.x, this.currPosition.y);
//            }
//        }
    }

    private void updatePosition() {
        if (this.orientation == Orientation.UP_LEFT) {
            if (Math.abs(leftCollision().x - moveToPoint.x) > 1) {
                this.currPosition.x -= 1;
            }
            if (Math.abs(leftCollision().y - moveToPoint.y) > 1) {
                this.currPosition.y -= 1;
            }
        } else if (this.orientation == Orientation.DOWN_LEFT) {
            if (Math.abs(leftCollision().x - moveToPoint.x) > 1) {
                this.currPosition.x -= 1;
            }
            if (Math.abs(leftCollision().y - moveToPoint.y) > 1) {
                this.currPosition.y += 1;
            }
        } else if (this.orientation == Orientation.UP_RIGHT) {
            if (Math.abs(rightCollision().x - ))
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
