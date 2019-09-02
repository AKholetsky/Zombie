package com.zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.zombie.config.MainIslandConfig;
import com.zombie.config.TileConfig;
import com.zombie.tile.Tiles;

public class LandMap {

    private MainIslandConfig config;
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapRenderer renderer;

    public LandMap(String config, OrthographicCamera camera) {
        this.config = new MainIslandConfig(config);
        this.camera = camera;
    }

    public void create() {
        Tiles tileTextures = new Tiles(config);
        tileTextures.prepare();
        this.map = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(config.mapHeightInTiles(), config.mapWidthInTiles(), config.tileWidth(), config.tileHeight());
        for (TileConfig tileConfig : normalize(config.tiles())) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setFlipHorizontally(tileConfig.flipHorizontally());
            cell.setFlipVertically(tileConfig.flipVertically());
            cell.setTile(new StaticTiledMapTile(tileTextures.tile(tileConfig.index())));
            layer.setCell(tileConfig.getLayerX(), tileConfig.getLayerY(), cell);
        }
        map.getLayers().add(layer);
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render() {
        renderer.setView(this.camera);
        renderer.render();
    }

    private Array<TileConfig> normalize(Array<TileConfig> configs) {
        for (TileConfig tileConfig : configs) {
            tileConfig.setLayerX(((tileConfig.x()) / this.config.tileWidth()));
            tileConfig.setLayerY(((tileConfig.y()) / this.config.tileHeight()));
        }
        return configs;
    }

    public int worldWidth() {
        return config.tileMapWidth();
    }

    public int worldHeight() {
        return config.tileMapHeight();
    }
}
