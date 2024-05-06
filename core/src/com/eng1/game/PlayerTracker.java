package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PlayerTracker {
    private Player player;
    private MapManager mapManager;

    public PlayerTracker(MapManager mapManager) {

        this.mapManager = mapManager;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void update(float delta) {
        // checkPlayerTile();

    }

    public void checkPlayerTile(float playerX, float playerY) {
        TiledMapTileLayer layer = (TiledMapTileLayer) mapManager.getCurrentMap().getLayers().get("map_change");
        TiledMapTileLayer.Cell cell = layer.getCell((int) (playerX / layer.getTileWidth()), (int) (playerY / layer.getTileHeight()));

        if (cell != null && cell.getTile().getProperties().containsKey("destination")) {
            String newPath = cell.getTile().getProperties().get("destination", String.class);
            String newEntry = cell.getTile().getProperties().get("spawn", String.class);
            mapManager.loadMap(newPath);
            Vector2 newSpawn = mapManager.findObjectPosition("entries", newEntry);
            if (newSpawn != null) {
                player.setPosition(newSpawn.x, newSpawn.y);
            }
        }
    }
}