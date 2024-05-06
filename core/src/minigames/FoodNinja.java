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
import com.badlogic.gdx.utils.ScreenUtils;
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
        obstaclesManager.SplitFoodTextures();
        mouse = new Mouse();
        
    }
    @Override
    public void show() {
        /*Sets up the elements of the screen when switched to this screen */
        
        canvas = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        TextField.TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.font = new BitmapFont(); 
        screenText = new TextArea("",textFieldStyle);
        screenText.setSize(200,20);
        screenText.setPosition(Gdx.graphics.getWidth()/2-screenText.getWidth()*2, Gdx.graphics.getHeight()/2 - screenText.getHeight()*2);
        timer = new TextArea("", textFieldStyle);
        timer.setSize(20,20);
        timer.setPosition(0, Gdx.graphics.getBackBufferHeight()-timer.getHeight()*2);
        canvas.addActor(screenText);
        canvas.addActor(timer);
        Gdx.input.setInputProcessor(canvas);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
            Update(delta, gameDuration);
            Draw((SpriteBatch)canvas.getBatch());
        }
        //This if statement is responsible for handling the end of the game 
        else if(miniGState == MinigameState.END){
            screenText.setText("Press space to go back to the main game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                obstaclesManager.ClearObstacles();
                miniGState = MinigameState.WAIT;
                parent.changeScreen(MenuState.APPLICATION);
            }
        }
        canvas.getBatch().end();
        canvas.draw();
        canvas.act();
    }

    public void Update(float delta,long gameDuration){
        mouse.Update(delta);
        if(gameDuration%3 == 0){
            obstaclesManager.SpawnFoodNinjaObstacles();
        }
        for(Obstacle obstacle : obstaclesManager.getObstacles()){
            checkObstacleIsWithinScreen(obstacle);
            if(mouse.getCircleBounds().contains(obstacle.getBounds())){
                obstacle.setDraw(false);
            }
            obstacle.Update(delta);
            obstacle.Move();
        }
        obstaclesManager.RemoveObstacle();
    }

    private void checkObstacleIsWithinScreen(Obstacle obstacle){
        if(obstacle.getPos().y  < 0){
            obstacle.setDraw(false);
        }
        if(obstacle.getPos().y + obstacle.getTexture().getRegionHeight()*2 >= Gdx.graphics.getBackBufferHeight()){
            obstacle.setDraw(false);
        }
        if(obstacle.getPos().x < 0){
            obstacle.setDraw(false);
        }
        if(obstacle.getPos().x + obstacle.getTexture().getRegionWidth()*2 >= Gdx.graphics.getBackBufferWidth()){
            obstacle.setDraw(false);
        }
    }

    @Override
    public void resize(int width, int height) {
       
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
      dispose();
    }

    @Override
    public void dispose() {
       canvas.dispose();
       Gdx.input.setInputProcessor(null);
    }

    public void Draw(SpriteBatch spriteBatch){
        obstaclesManager.Draw(spriteBatch);
    }
    
}
