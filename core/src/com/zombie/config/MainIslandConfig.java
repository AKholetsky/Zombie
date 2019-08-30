package com.zombie.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

public class MainIslandConfig implements MapConfig {

    private FileHandle file;
    private XmlReader reader;
    private XmlReader.Element root;
    private String mainIslandPath = "maps/main_island/";

    public MainIslandConfig(String path) {
        this.reader = new XmlReader();
        this.file = Gdx.files.internal(path);
    }

    @Override
    public int tileHeight() {
        return parse().getInt("tileHeight");
    }

    @Override
    public int tileWidth() {
        return parse().getInt("tileWidth");
    }

    @Override
    public FileHandle islandImage () {
        String imagePath = parse().get("image");
        return Gdx.files.internal(mainIslandPath + imagePath);
    }

    private int tileMapHeight() {
        return parse().getInt("tileMapHeight");
    }

    private int tileMapWidth() {
        return parse().getInt("tileMapWidth");
    }

    @Override
    public int mapHeightInTiles() {
        return tileMapHeight() / tileHeight();
    }

    @Override
    public int mapWidthInTiles() {
        return tileMapWidth() / tileWidth();
    }

    @Override
    public int tilesPerAtlasRow() {
        return parse().getInt("tilesPerAtlasRow");
    }

    @Override
    public int tilesPerAtlasColumn() {
        return parse().getInt("tilesPerAtlasColumn");
    }

    @Override
    public int tileBorderSize() {
        return parse().getInt("tileBorderSize");
    }

    private int offsetX() {
        XmlReader.Element point = getPoint(offsetPoint());
        if (point != null) {
            return point.getInt("x");
        }
        return 0;
    }

    private int offsetY() {
        XmlReader.Element point = getPoint(offsetPoint());
        if (point != null) {
            return point.getInt("y");
        }
        return 0;
    }

    private XmlReader.Element getPoint(XmlReader.Element offset) {
        if (offset != null) {
            return offset.getChildByName("Point");
        }
        return null;
    }

    private XmlReader.Element offsetPoint() {
        return parse().getChildByName("offset");
    }



    public Array<TileConfig> tiles() {
        Array<XmlReader.Element> tiles = parse().getChildrenByNameRecursively("Tile");
        Array<TileConfig> result = new Array<>(tiles.size);
        for (XmlReader.Element tile : tiles ) {
            result.add(
                    new TileConfig(
                            tile.getBoolean("flipHorizontal"),
                            tile.getBoolean("flipVertical"),
                            tile.getInt("index"),
                            tile.getInt("x") - offsetX(),
                            tile.getInt("y") - offsetY()
                    )
            );
        }
        return result;
    }

    private XmlReader.Element parse() {
        if (root == null) {
            root = this.reader.parse(this.file);
        }
        return root;
    }
}
