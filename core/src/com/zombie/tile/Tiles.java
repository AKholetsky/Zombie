package com.zombie.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zombie.config.MapConfig;

import java.util.HashMap;
import java.util.Map;

public class Tiles {

    private final MapConfig config;
    private final Map<Integer, TextureRegion> tiles;

    public Tiles(MapConfig config) {
        this.config = config;
        tiles = new HashMap<>(config.tilesPerAtlasColumn() * config.tilesPerAtlasRow());
    }

    public void prepare() {
        TextureRegion[][] splitTiles = TextureRegion.split(
                new Texture(this.config.islandImage()),
                this.config.tileWidth() + this.config.tileBorderSize() * 2,
                this.config.tileHeight() + this.config.tileBorderSize() * 2);
        int mapIndex = 1;
        for(int rowIndex = 0; rowIndex < this.config.tilesPerAtlasRow(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < this.config.tilesPerAtlasColumn(); columnIndex++) {
                TextureRegion textureRegion = splitTiles[rowIndex][columnIndex];
                textureRegion.flip(false, true);
                this.tiles.put(mapIndex, textureRegion);
                mapIndex += 1;
            }
        }
    }

    public TextureRegion tile(int index) {
        return tiles.get(index);
    }


}
