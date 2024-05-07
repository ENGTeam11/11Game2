package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Play implements Screen {
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private MapManager mapManager;
    private Player player;
    private BitmapFont displayDateTime;
    public static String selectedCharacter;
    private PlayerTracker playerTracker;

    public Play() {
        Activity.createActivities();
        camera = new OrthographicCamera();
        AssetManager assetManager = new AssetManager();
        mapManager = new MapManager(assetManager, camera);
        mapManager.loadMap("maps/map1/map1.tmx");
        playerTracker = new PlayerTracker(mapManager);
        player = new Player(new Sprite(new Texture("playerCharacters/playerCharacter1.png")), playerTracker, mapManager);
        playerTracker.setPlayer(player);
        displayDateTime = new BitmapFont();
    }

    public static void setSelectedCharacter(String character) {
        selectedCharacter = character;
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(player);
    }
    

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        mapManager.render();
        player.update(delta, mapManager);
        
        renderer.getBatch().begin();
        renderer.getBatch().setProjectionMatrix(camera.combined);
        player.draw(renderer.getBatch());
        displayDateTime.draw(renderer.getBatch(), "TEXT", 10, 20);
        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = 16f / 9f;
        float scale = 1f;
        float viewportHeight = height;
        float viewportWidth = viewportHeight * aspectRatio;

        if (viewportWidth > width) {
            scale = width / viewportWidth;
            viewportWidth = width;
            viewportHeight = viewportWidth / aspectRatio;
        }

        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.update();

        if (mapManager.getCurrentMap() != null) {
            renderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap());
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapManager.dispose();
        player.getTexture().dispose();
        displayDateTime.dispose();
    }
}

