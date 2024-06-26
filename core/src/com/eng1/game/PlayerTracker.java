package com.eng1.game;

import javax.swing.text.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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

    /**
     * checks if the player is standing on a map transport tile and sends them to the new map and spawn according to the tile
     * @param playerX players x position
     * @param playerY players y position
     */
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
    /**
     * checks if the player is currently stood on an activity layer object and if so returns the name of the object
     * @param Position vector position of the player
     * @param width the player's width
     * @param height the player's height
     * @return a String of the collided objects name. null if not colliding with any
     */
    public String checkPlayerActivity(Vector2 Position, float width, float height) {
        MapLayer ObjectLayer = mapManager.getCurrentMap().getLayers().get("activities");
        Rectangle playerBounds = new Rectangle(Position.x, Position.y, width, height);
        if (ObjectLayer != null) {
            MapObjects objects = ObjectLayer.getObjects();

            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    if (Intersector.overlaps(rectangle, playerBounds)) {
                        if (object.getName() != null){
                            return object.getName();
                        }
                    }
                }
            }
        }
        return null;
    }
}