package com.eng1.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import eng1.model.views.MenuScreen;


public class Play implements Screen {
    static final int MAPHEIGHT = 1080;
    static final int MAPWIDTH = 1920;
    
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private MapManager mapManager;
    private Player player;
    private BitmapFont displayDateTime;
    public static String selectedCharacter; //the name of the selected character, used for player sprite selecting
    private PlayerTracker playerTracker;
    private GameUI gameUI;
    private Skin skin;
    private GameStats gameStats;
    private HeslingtonHustle heslingtonHustle;
    private BitmapFont interactionsPrompt;
    boolean showInteractPrompt = false;

    public Play(HeslingtonHustle game) {
        heslingtonHustle = game;
        Activity.createActivities();
        camera = new OrthographicCamera();
        AssetManager assetManager = new AssetManager();
        mapManager = new MapManager(assetManager, camera);
        mapManager.loadMap("maps/map1/map1.tmx");
        playerTracker = new PlayerTracker(mapManager);
        player = new Player(this, playerTracker, mapManager);
        playerTracker.setPlayer(player);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameUI = new GameUI(skin);
        gameStats = new GameStats();
        GameStats.initializeGameTime();
        GameStats.initialiseStreaks();

        MenuScreen.setStartNewGame(false); // Set start new game to false, so a new instance is not created everytime
        interactionsPrompt = new BitmapFont();
        interactionsPrompt.getData().setScale(2f);
        interactionsPrompt.setColor(1, 1, 1, 1);

    }

    public static void setSelectedCharacter(String characterName) {
        selectedCharacter = characterName;
    }
    public static String getSelectedCharacter() {
        return selectedCharacter;
    }

    @Override
    /**
     * sets up the necessary features when the screen is switched to
     */
    public void show() {
        Gdx.input.setInputProcessor(player);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameUI.getStage());
        inputMultiplexer.addProcessor(player);
        Gdx.input.setInputProcessor(inputMultiplexer);


    }
    

    @Override
    /**
     * updates draws the play screen
     * @param delta the time since the last render
     */
    public void render(float delta) {
        //checks if player has paused game
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            heslingtonHustle.changeScreen(MenuState.PAUSE);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        GameStats.initializeGameTimeFlow(delta); //updates in game clock
        mapManager.render();
        player.update(delta, mapManager);
        gameUI.render(delta);
        mapManager.boundaryCheck(player); // checks the player has not left the camera boundaries
        
        renderer.getBatch().begin();
        renderer.getBatch().setProjectionMatrix(camera.combined);
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        if (showInteractPrompt) {
            renderer.getBatch().begin();
            interactionsPrompt.draw(renderer.getBatch(), "Press E to Interact", player.getX(), player.getY() + 50);
            renderer.getBatch().end();
            Gdx.app.log("InteractionPrompt", "Drawing at: " + player.getX() + ", " + player.getY() + 50);
        }
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = 16f / 9f;
        float scale = 1f;
        float viewportHeight = height;
        float viewportWidth = viewportHeight * aspectRatio;
        float resScale = MAPHEIGHT/viewportHeight;//sets the scale to be proportional to the resolution of the screen 
        mapManager.setResScale(resScale);

        if (viewportWidth > width) {
            scale = width / viewportWidth;
            viewportWidth = width;
            viewportHeight = viewportWidth / aspectRatio;
        }
        camera.zoom = 1;
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.position.set(MAPWIDTH / 2f, MAPHEIGHT / 2f, 0);
        mapManager.adjustCamera();
        camera.update();
        gameUI.resize(width, height);

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
        gameUI.dispose();
        interactionsPrompt.dispose();
    }
}

