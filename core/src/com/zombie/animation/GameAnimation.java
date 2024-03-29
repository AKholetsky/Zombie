package com.zombie.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.zombie.config.animation.AnimationConfig;

public class GameAnimation {

    public final AnimationConfig config;
    private Animation<TextureRegion> animation;

    public GameAnimation(AnimationConfig configFile) {
        this.config = configFile;
    }

    public void create() {
        Texture animationTexture = new Texture(this.config.animationTexture());
        TextureRegion[][] sprites = TextureRegion.split(animationTexture, this.config.frameWidth(), this.config.frameHeight());

        animation = new Animation<>(0.05f, sprites[0]);
    }

    public TextureRegion currentFrame(float stateTime) {
        TextureRegion keyFrame = animation.getKeyFrame(stateTime, true);
        if (!keyFrame.isFlipY()) {
            keyFrame.flip(false, true);
        }
        return keyFrame;
    }

    public Vector3 centerPosition(Vector3 currPosition) {
        return new Vector3(currPosition.x - config.frameWidth() / 2f,
                currPosition.y - config.frameHeight() /2f,
                0);
    }
}
