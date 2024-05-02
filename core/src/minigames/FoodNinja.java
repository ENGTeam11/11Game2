package minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.MenuState;
import com.eng1.game.Play;

import minigames.minigames_components.MinigameState;
import minigames.minigames_components.Mouse;
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
    private final int GAME_LENGTH_SECONDS = 30;

    public FoodNinja(HeslingtonHustle inParent){
        parent = inParent;
        miniGState = MinigameState.WAIT;
        obstaclesManager = new ObstacleSpawner(Gdx.);
        mouse = new Mouse();
        canvas = new Stage(new StretchViewport(800, 600));
    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(canvas);
    }

    @Override
    public void render(float delta) {
        canvas.getBatch().begin();
        //Gets the current time from the system clock
        Instant gameTime = Instant.now();
        //This if statement takes care of the state before the game begins where the player has to click space to begin
        if(miniGState == MinigameState.WAIT){
            if(Gdx.input.isKeyPressed(62)){
                miniGState = MinigameState.END;
                startTime = Instant.now();
            }
        }
        //This if statement takes care of mini game logic 
        else if(miniGState == MinigameState.START){
            /*checks the difference between the start time and end time of the game in seconds since 1970 and
            checks if the difference is longer than the set game length in seconds*/
            if(gameTime.getEpochSecond() - startTime.getEpochSecond() >= GAME_LENGTH_SECONDS){
                miniGState = MinigameState.END;
            }
            mouse.Update(delta);
        }
        //This if statement is responsible for handling the end of the game 
        else if(miniGState == MinigameState.END){
            parent.changeScreen(MenuState.APPLICATION);
        }
        canvas.getBatch().end();
    }

    public void Update(){

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
       
    }

    public void Draw(SpriteBatch spriteBatch){
        obstaclesManager.Draw(spriteBatch);
    }
    
}
