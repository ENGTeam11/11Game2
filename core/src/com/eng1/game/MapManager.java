package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapManager {
    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private AssetManager assetManager;

    public MapManager(AssetManager assetManager, OrthographicCamera camera) {
        this.assetManager = assetManager;
        this.camera = camera;
        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
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
    public void loadMap (String mapPath) {
        if (currentMap != null) {
            currentMap.dispose();
        }
        if (!assetManager.isLoaded(mapPath)) {
            System.out.println("Loading" + mapPath);
            assetManager.load(mapPath, TiledMap.class);
            assetManager.finishLoading();
        }
        currentMap = assetManager.get(mapPath, TiledMap.class);
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);
    }
    public TiledMap getCurrentMap() {
        return currentMap;
    }
    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
    public void dispose() {
        mapRenderer.dispose();
    }
}