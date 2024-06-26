package minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.MenuState;

import minigames.minigames_components.MinigameState;
import minigames.minigames_components.Mouse;
import minigames.minigames_components.Obstacle;
import minigames.minigames_components.ObstacleSpawner;

public class FoodNinja implements Screen {
    //holds the parent so it can switch back to the game screen
    private HeslingtonHustle parent;
    //players mouse
    private Mouse mouse;
    //responsible for the game state
    private MinigameState miniGState;
    private ObstacleSpawner obstaclesManager;
    private Instant startTime;
    private Stage canvas;
    private TextArea screenText,timer;
    private final int GAME_LENGTH_SECONDS = 30;

    public FoodNinja(HeslingtonHustle inParent){
        parent = inParent;
        miniGState = MinigameState.WAIT;
        obstaclesManager = new ObstacleSpawner(new Texture(Gdx.files.internal("minigame/Fruit.png")));
        obstaclesManager.splitFoodTextures();
        mouse = new Mouse();
        
    }
    @Override
    public void show() {
        /*Sets up the elements of the screen when switched to this screen */
        //The stage where the game will be drawn
        canvas = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        //Style of the text area
        TextField.TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.font = new BitmapFont(); 

        //Sets the text area for the start of the game and end of the game 
        screenText = new TextArea("",textFieldStyle);
        screenText.setSize(300,20);
        screenText.setPosition(Gdx.graphics.getWidth()/2-screenText.getWidth()/2, Gdx.graphics.getHeight()/2 - screenText.getHeight()/2);

        //Sets the text area for the timer of the game
        timer = new TextArea("", textFieldStyle);
        timer.setSize(20,20);
        timer.setPosition(0, Gdx.graphics.getBackBufferHeight()-timer.getHeight()*2);

        //Adds the text areas to the stage
        canvas.addActor(screenText);
        canvas.addActor(timer);

        miniGState = MinigameState.WAIT;

        //sets the input processor to the canvas
        Gdx.input.setInputProcessor(canvas);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            parent.changeScreen(MenuState.PAUSE);
        }
        //Clears the screen before drawing each frame
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //begins the drawing on the stage
        canvas.getBatch().begin();
        //Gets the current time from the system clock
        Instant gameTime = Instant.now();
        //This if statement takes care of the state before the game begins where the player has to click space to begin
        if(miniGState == MinigameState.WAIT){ 
            screenText.setText("Press space to start the game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                miniGState = MinigameState.START;
                startTime = Instant.now();
                screenText.setText("");
            }
        }
        //This if statement takes care of mini game logic 
        else if(miniGState == MinigameState.START){
            long gameDuration = gameTime.getEpochSecond() - startTime.getEpochSecond();
            /*checks the difference between the start time and end time of the game in seconds since 1970 and
            checks if the difference is longer than the set game length in seconds*/
            timer.setText(String.valueOf(GAME_LENGTH_SECONDS-(int)gameDuration));
            if(gameDuration >= GAME_LENGTH_SECONDS){
                miniGState = MinigameState.END;
            }
            //updates the game objects
            update(delta, gameDuration);
            //draws game objects
            draw((SpriteBatch)canvas.getBatch());
        }
        //This if statement is responsible for handling the end of the game 
        else if(miniGState == MinigameState.END){
            screenText.setText("Press space to go back to the main game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                miniGState = MinigameState.WAIT;
                parent.changeScreen(MenuState.APPLICATION);
            }
        }
        canvas.getBatch().end();
        canvas.draw();
        canvas.act();
    }

    public void update(float delta,long gameDuration){
        //updates the mouse
        mouse.update(delta);
        //spawns obstacles everytime the game duration is a multiple of 5 
        if(gameDuration%5 == 0){
            obstaclesManager.spawnFoodNinjaObstacles();
        }

        //loop for updating obstacles
        for(Obstacle obstacle : obstaclesManager.getObstacles()){
            if(mouse.getCircleBounds().contains(obstacle.getBounds())){
                obstacle.setDraw(false);
            }
            obstacle.update(delta);
            obstacle.move();
        }
        obstaclesManager.removeObstacle();
    }

    @Override
    public void resize(int width, int height) {
       //not implemented as not needed
    }

    @Override
    public void pause() {
        //not implemented as not needed
    }

    @Override
    public void resume() {
        //not implemented as not needed
    }

    @Override
    public void hide() {
      dispose();
    }

    @Override
    public void dispose() {
        //releases the unused resources
       canvas.dispose();
       obstaclesManager.clearObstacles();
       Gdx.input.setInputProcessor(null);
    }

    public void draw(SpriteBatch spriteBatch){
        //draws the obstacles 
        obstaclesManager.draw(spriteBatch);
    }
    
}
