package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap;

public class MapManager {
    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private AssetManager assetManager;
    private ObjectMap<String, Float> mapScales;
    private String currentMapPath;
    private Vector2 bounds;
    private float scale, resScale;
    
    public MapManager(AssetManager assetManager, OrthographicCamera camera) {
        this.assetManager = assetManager;
        this.camera = camera;
        resScale = 1;
        bounds = new Vector2();
        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        initializeMapScales();
    }

    public void initializeMapScales(){
        mapScales = new ObjectMap<>();
        mapScales.put("maps/home/home.tmx", 3.5f);
        mapScales.put("maps/gym/gym.tmx", 3.2f);
        mapScales.put("maps/cs/computer-science-building.tmx", 3.2f);
        mapScales.put("maps/piazza/piazza.tmx", 3.2f);
    }

    public Vector2 findObjectPosition(String layerName, String objectName) {
        MapLayer layer = currentMap.getLayers().get(layerName);
        if (layer != null) {
            for (MapObject object : layer.getObjects()) {
                if (objectName.equals(object.getName()) && object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    return new Vector2(rect.x , rect.y); // Assuming the bottom-left corner as the reference
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
        currentMapPath = mapPath;
        scale = mapScales.get(mapPath, 1.0f);
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);
        adjustCamera();
        bounds.set(Gdx.graphics.getWidth()/3/scale, Gdx.graphics.getHeight()/3/scale);
    }
    public void adjustCamera(){
        camera.zoom = resScale / scale;
        camera.update();
    }

    public void setResScale(float resScale){
        this.resScale = resScale;
    }

    public void boundaryCheck(Player player){
        if (scale != 1){
            Vector3 position = camera.position;
            
            if (position.x > player.getX() + bounds.x){
                position.x = player.getX() + bounds.x;
            }
            else if (position.x < player.getX() - bounds.x){
                position.x = player.getX() - bounds.x;
            }

            if (position.y > player.getY() + bounds.y){
                position.y = player.getY() + bounds.y;
            }
            else if (position.y < player.getY() - bounds.y){
                position.y = player.getY() - bounds.y;
            }

            camera.position.set(position);
            camera.update();
        }   
    }

    public boolean inRegion(Vector2 Position, float width, float height, String layerName) {
        Rectangle playerBounds = new Rectangle(Position.x, Position.y, width, height);
        MapLayer ObjectLayer = currentMap.getLayers().get(layerName);
        if (ObjectLayer != null) {
            MapObjects objects = ObjectLayer.getObjects();

            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    if (Intersector.overlaps(rectangle, playerBounds)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Vector2 GetBounds(){
        return bounds;
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public float getCurrentScale(){
        return mapScales.get(currentMapPath, 1.0f);
    }

    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
    public void dispose() {
        mapRenderer.dispose();
    }
}