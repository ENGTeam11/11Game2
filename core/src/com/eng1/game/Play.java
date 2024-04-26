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

    public Play() {
        camera = new OrthographicCamera();
        AssetManager assetManager = new AssetManager();
        mapManager = new MapManager(assetManager, camera);
        mapManager.loadMap("maps/map1/map1.tmx");
        player =  new Player(new Sprite(new Texture("playerCharacters/playerCharacter1.png")), mapManager);
        mapManager.setPlayer(player);
        displayDateTime = new BitmapFont();
        renderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap());
    }


    public static void setSelectedCharacter(String character) {
        selectedCharacter = character;
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        mapManager.render();
        player.update(delta); // Ensure player logic is updated

        renderer.getBatch().begin();
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

