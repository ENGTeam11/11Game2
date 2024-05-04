package minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
        obstaclesManager.SplitFoodTextures();
        mouse = new Mouse();
        
    }
    @Override
    public void show() {
        /*Sets up the elements of the screen when switched to this screen */
        canvas = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        TextField.TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.fontColor = Color.BLACK;
        screenText = new TextArea("",textFieldStyle);
        screenText.setPosition(Gdx.graphics.getWidth()/2-screenText.getWidth()*2, Gdx.graphics.getHeight()/2 - screenText.getHeight()*2);
        timer = new TextArea("", textFieldStyle);
        timer.setPosition(0, timer.getWidth()*2);
        canvas.addActor(screenText);
        Gdx.input.setInputProcessor(canvas);
    }

    @Override
    public void render(float delta) {
        canvas.getBatch().begin();
        //Gets the current time from the system clock
        Instant gameTime = Instant.now();
        //This if statement takes care of the state before the game begins where the player has to click space to begin
        if(miniGState == MinigameState.WAIT){
            screenText.setText("Press space to start the game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                miniGState = MinigameState.END;
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
            obstaclesManager.RemoveObstacle();
        }
        //This if statement is responsible for handling the end of the game 
        else if(miniGState == MinigameState.END){
            screenText.setText("Press space to go back to the main game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                obstaclesManager.ClearObstacles();
                parent.changeScreen(MenuState.APPLICATION);
            }
        }
        canvas.getBatch().end();
        canvas.draw();
        canvas.act();
    }

    public void Update(float delta,long gameDuration){
        if(gameDuration%2 == 0){
            obstaclesManager.SpawnFoodNinjaObstacles();
        }
        for(Obstacle obstacle : obstaclesManager.getObstacles()){
            if(obstacle.getBounds().Contains(mouse.getCircleBounds())){
                obstacle.setDraw(false);
            }
            obstacle.CalcTrajectory(delta);
            obstacle.Update(delta);
        }
        mouse.Update(delta);
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
      
    }

    @Override
    public void dispose() {
       canvas.dispose();
    }

    public void Draw(SpriteBatch spriteBatch){
        obstaclesManager.Draw(spriteBatch);
    }
    
}
