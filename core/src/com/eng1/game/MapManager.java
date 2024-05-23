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
    private TiledMap currentMap; // the map that is currently loaded
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private AssetManager assetManager;
    private ObjectMap<String, Float> mapScales; // a hashmap with scales for the interior maps
    private String currentMapPath; // the path directory of the current map
    private Vector2 bounds; // how far the player can move from the center of the camera before it follows. only used in interior maps
    private float scale, resScale;
    
    public MapManager(AssetManager assetManager, OrthographicCamera camera) {
        this.assetManager = assetManager;
        this.camera = camera;
        resScale = 1;
        bounds = new Vector2();
        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        initializeMapScales();
    }

    /**
     * initialises the mapScales hashmap and adds scales for all interior maps. the maps directory is used as the key and the scales are floats
     */
    public void initializeMapScales(){
        mapScales = new ObjectMap<>();
        mapScales.put("maps/home/home.tmx", 3.5f);
        mapScales.put("maps/gym/gym.tmx", 3.2f);
        mapScales.put("maps/cs/computer-science-building.tmx", 3.2f);
        mapScales.put("maps/piazza/piazza.tmx", 3.2f);
    }

    /**
     * finds the position of an object on the map
     * @param layerName the name of the Tiled layer the object is on
     * @param objectName the name of the object
     * @return a vector with the coordinates of the object
     */
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

    /**
     * clears out the current map and loads in a new one
     * @param mapPath a string of the directory of the map to be loaded
     */
    public void loadMap (String mapPath) {
        //removes current map
        if (currentMap != null) {
            currentMap.dispose();
        }
        //checks that the new map has been loaded before and loads it if not
        if (!assetManager.isLoaded(mapPath)) {
            System.out.println("Loading" + mapPath);
            assetManager.load(mapPath, TiledMap.class);
            assetManager.finishLoading();
        }
        //adds all of the tiled layers into the mapRenderer
        currentMap = assetManager.get(mapPath, TiledMap.class);
        currentMapPath = mapPath;
        scale = mapScales.get(mapPath, 1.0f); //sets the scale of the map. map is scaled at 1 if it is not in the hashmap
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);
        
        //zooms the camera and sets the camera boundaries
        adjustCamera();
        bounds.set(Gdx.graphics.getWidth()/3/scale, Gdx.graphics.getHeight()/3/scale);
        //checks if the map loaded is map 7 to activate the walk stat
        System.out.println(currentMapPath);
        if (currentMapPath.equals("maps/map7/map7.tmx")){
            GameStats.setWalked(true);
        }
    }

    /**
     * sets the camera zoom based on the map scale
     */
    public void adjustCamera(){
        camera.zoom = resScale / scale;
        camera.update();
    }

    public void setResScale(float resScale){
        this.resScale = resScale;
    }
    
    /**
     * checks that the player is within the cameras boundaries and adjusts the position of the camera accordingly
     * @param player the player character
     */
    public void boundaryCheck(Player player){
        Vector3 position = camera.position;
        if (scale != 1){
            
            
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

        else{
            position.x = Play.MAPWIDTH / 2;
            position.y = Play.MAPHEIGHT / 2;
        }
    }

    /**
     * checks whether the player is currently overlapping with any object in a certain layer
     * @param Position the players position
     * @param width the player width
     * @param height the player height
     * @param layerName the name of the layer we are checking for
     * @return Boolean true if player is overlapping with object layer, false if not
     */
    public boolean inRegion(Vector2 Position, float width, float height, String layerName) {
        Rectangle playerBounds = new Rectangle(Position.x, Position.y, width, height); //rectangle to represent player
        MapLayer ObjectLayer = currentMap.getLayers().get(layerName);
        if (ObjectLayer != null) {
            //checks every object in the layer
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
    /**
     * renders the map
     */
    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
    public void dispose() {
        mapRenderer.dispose();
    }
}