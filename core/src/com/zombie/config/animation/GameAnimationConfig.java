package com.zombie.config.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

public class GameAnimationConfig implements AnimationConfig {

    private FileHandle file;
    private XmlReader reader;

    private XmlReader.Element root;
    private final String rootPath;

    public GameAnimationConfig(String rootPath, String configPath) {
        this.reader = new XmlReader();
        this.rootPath = rootPath;
        this.file = Gdx.files.internal(rootPath + configPath);
    }

    @Override
    public FileHandle animationTexture() {
        String animationTexture = parse().get("animationTexture");
        return Gdx.files.internal(this.rootPath + animationTexture);
    }

    //Width and height for each frame the same.
    //We can iterate over each frame and somehow find optimal width and height
    //but it the same for each frame.
    @Override
    public int frameWidth() {
        return frames().get(0).getInt("width");
    }

    @Override
    public int frameHeight() {
        return frames().get(0).getInt("height");
    }

    private Array<XmlReader.Element> frames() {
        return parse().getChildrenByNameRecursively("Frame");
    }


    private XmlReader.Element parse() {
        if (root == null) {
            root = this.reader.parse(this.file);
        }
        return root;
    }
}
