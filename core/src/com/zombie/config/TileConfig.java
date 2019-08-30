package com.zombie.config;

public class TileConfig {

    private final boolean flipHorizontally;
    private final boolean flipVertically;
    private final int index;
    private final int x;
    private final int y;
    private int layerX;
    private int layerY;

    public TileConfig(boolean flipHorizontally, boolean flipVertically, int index, int x, int y) {
        this.flipHorizontally = flipHorizontally;
        this.flipVertically = flipVertically;
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public boolean flipHorizontally() {
        return this.flipHorizontally;
    }

    public boolean flipVertically() {
        return flipVertically;
    }

    public int index() {
        return index;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int getLayerX() {
        return layerX;
    }

    public void setLayerX(int layerX) {
        this.layerX = layerX;
    }

    public int getLayerY() {
        return layerY;
    }

    public void setLayerY(int layerY) {
        this.layerY = layerY;
    }
}
