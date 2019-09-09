package com.zombie.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;
import com.zombie.controller.BiomController;
import com.zombie.controller.MoveAndCut;

public class WalkingZombie implements MoveAndCut {

    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final BiomController biomController;
    private final GameAnimation walkingUpAnimation;
    private final GameAnimation walkingDownAnimation;
    private final GameAnimation stand;
    private final GameAnimation cut;
    private Vector3 currPosition;
    private Vector3 moveToPoint;
    private float deltaTime;
    private Orientation orientation = Orientation.UP_LEFT;
    private boolean cutTheTree = false;
    private boolean walk = false;
    private Tree treeToBeCutted;
    private Vector3 positionBeforeCut;
    private final GameAnimation walkWoodUp;
    private final GameAnimation walkWoodDown;
    private boolean returnTree = false;

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
                         GameAnimation walkWoodUp,
                         GameAnimation walkWoodDown,
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
        this.positionBeforeCut = new Vector3();
        this.walkWoodUp = walkWoodUp;
        this.walkWoodDown = walkWoodDown;
    }

    public void create() {
        this.walkingUpAnimation.create();
        this.walkingDownAnimation.create();
        this.walkWoodUp.create();
        this.walkWoodDown.create();
        this.stand.create();
        this.cut.create();
    }

    //Point at down center of sprite
    private Vector3 centerCollision() {
        return new Vector3(
                this.currPosition.x + this.walkingUpAnimation.config.frameWidth() / 2f,
                this.currPosition.y + this.walkingUpAnimation.config.frameHeight(),
                0
        );
    }

    @Override
    public void moveTo(Vector3 moveToPos) {
        moveToPoint = moveToPos;
        walk = true;
        returnTree = false;
    }

    @Override
    public void moveAndCut(Tree tree) {
        walk = true;
        moveToPoint = tree.centerCollision();
        cutTheTree = true;
        treeToBeCutted = tree;
        returnTree = true;
    }

    private Orientation guessOrientation() {
        if (moveToPoint.x <= centerCollision().x && moveToPoint.y <= centerCollision().y) {
            return Orientation.UP_LEFT;
        } else if (moveToPoint.x > centerCollision().x && moveToPoint.y <= centerCollision().y) {
            return Orientation.UP_RIGHT;
        } else if (moveToPoint.x <= centerCollision().x && moveToPoint.y > centerCollision().y) {
            return Orientation.DOWN_LEFT;
        } else {
            return Orientation.DOWN_RIGHT;
        }
    }

    public void draw(float timeStamp) {
        this.deltaTime += timeStamp;
        this.orientation = guessOrientation();
        updatePosition();
        if (walk) {
            TextureRegion walking = guessTexture();
            this.spriteBatch.draw(
                    walking,
                    this.currPosition.x,
                    this.currPosition.y);
        } else {
            if (cutTheTree) {
                this.spriteBatch.draw(cut.currentFrame(deltaTime), this.currPosition.x, this.currPosition.y);
                boolean cutted = treeToBeCutted.cut();
                if (cutted) {
                    cutTheTree = false;
                    returnTree = true;
                    walk = true;
                    moveToPoint = positionBeforeCut;
                }
            } else {
                this.spriteBatch.draw(this.stand.currentFrame(deltaTime), this.currPosition.x, this.currPosition.y);
            }
        }
    }

    private void updatePosition() {
        if (walk) {
            if (this.orientation == Orientation.UP_LEFT) {
                if (Math.abs(centerCollision().x - moveToPoint.x) > 1) {
                    this.currPosition.x -= 1;
                }
                if (Math.abs(centerCollision().y - moveToPoint.y) > 1) {
                    this.currPosition.y -= 1;
                }
            } else if (this.orientation == Orientation.DOWN_LEFT) {
                if (Math.abs(centerCollision().x - moveToPoint.x) > 1) {
                    this.currPosition.x -= 1;
                }
                if (Math.abs(centerCollision().y - moveToPoint.y) > 1) {
                    this.currPosition.y += 1;
                }
            } else if (this.orientation == Orientation.UP_RIGHT) {
                if (Math.abs(centerCollision().x - moveToPoint.x) > 1) {
                    this.currPosition.x += 1;
                }
                if (Math.abs(centerCollision().y - moveToPoint.y) > 1) {
                    this.currPosition.y -= 1;
                }
            } else {
                if (Math.abs(centerCollision().x - moveToPoint.x) > 1) {
                    this.currPosition.x += 1;
                }
                if (Math.abs(centerCollision().y - moveToPoint.y) > 1) {
                    this.currPosition.y += 1;
                }
            }
        }

        if (Math.abs(centerCollision().x - moveToPoint.x) <= 1 &&
        Math.abs(centerCollision().y - moveToPoint.y) <= 1) {
            walk = false;
        }
    }

    private TextureRegion guessTexture() {
        TextureRegion walkingUpTexture = returnTree ?
                this.walkWoodUp.currentFrame(deltaTime) :
                this.walkingUpAnimation.currentFrame(deltaTime);
        TextureRegion walkingDownTexture = returnTree ?
                this.walkWoodDown.currentFrame(deltaTime) :
                this.walkingDownAnimation.currentFrame(deltaTime);
        switch (this.orientation) {
            case UP_LEFT: {
                if (walkingUpTexture.isFlipX()) {
                    walkingUpTexture.flip(true, false);
                }
                return walkingUpTexture;
            }
            case UP_RIGHT: {
                if (!walkingUpTexture.isFlipX()) {
                    walkingUpTexture.flip(true, false);
                }
                return walkingUpTexture;
            }
            case DOWN_RIGHT: {
                if (!walkingDownTexture.isFlipX()) {
                    walkingDownTexture.flip(true, false);
                }
                return walkingDownTexture;
            }
            default: {
                if (walkingDownTexture.isFlipX()) {
                    walkingDownTexture.flip(true, false);
                }
                return walkingDownTexture;
            }
        }
    }
}
