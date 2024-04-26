package com.eng1.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.swing.*;

public class MapManager {
    private TiledMap currentMap;
    private AssetManager assetManager;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Player player;
    private String currentMapPath;


    public MapManager(AssetManager assetManager, OrthographicCamera camera) {
        this.assetManager = assetManager;
        this.camera = camera;
        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        this.mapRenderer = new OrthogonalTiledMapRenderer(null);
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void loadMap(String mapPath) {
        if (currentMap != null) {
            currentMap.dispose(); // Dispose of the old map
        }
        assetManager.load(mapPath, TiledMap.class);
        assetManager.finishLoading(); // Block until all assets are loaded
        currentMap = assetManager.get(mapPath, TiledMap.class);
        mapRenderer.setMap(currentMap);
        currentMapPath = mapPath;
    }

    public Vector2 findObjectPosition(String layerName, String objectName) {
        MapLayer layer = currentMap.getLayers().get(layerName);
        if (layer != null) {
            for (MapObject object : layer.getObjects()) {
                if (objectName.equals(object.getName()) && object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    return new Vector2(rect.x, rect.y); // Assuming the bottom-left corner as the reference
                }
            }
        }
        return null; // Object not found
    }
    public void changeMap(String newPath, String destinationName) {
        if (currentMap != null) {
            currentMap.dispose();
        }
        loadMap(newPath);
        Vector2 destination = findObjectPosition("map_change_points", destinationName);
        if (destination != null) {
            player.setPosition(destination.x, destination.y);
        }
    }

    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose() {
        if (currentMap != null) {
            currentMap.dispose();
        }
        mapRenderer.dispose();
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }
}
