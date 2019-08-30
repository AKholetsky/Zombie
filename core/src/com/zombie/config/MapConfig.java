package com.zombie.config;

import com.badlogic.gdx.files.FileHandle;

public interface MapConfig {

    FileHandle islandImage();

    int tileHeight();

    int tileWidth();

    int mapHeightInTiles();

    int mapWidthInTiles();

    int tilesPerAtlasRow();

    int tilesPerAtlasColumn();

    int tileBorderSize();

}
