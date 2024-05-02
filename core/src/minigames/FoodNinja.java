package minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.eng1.game.Play;

import minigames.minigames_components.MinigameState;
import minigames.minigames_components.Mouse;
import minigames.minigames_components.ObstacleSpawner;

public class FoodNinja implements Screen {
    private Play gameState;
    private Mouse mouse;
    private MinigameState miniGState;
    private ObstacleSpawner obstaclesManager;
    private Instant startTime;
    private Stage canvas;
    private final int GAME_LENGTH_SECONDS = 30;

    public FoodNinja(Play game){
        gameState = game;
        miniGState = MinigameState.WAIT;
        obstaclesManager = new ObstacleSpawner();
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
        Instant gameTime = Instant.now();
        if(miniGState == MinigameState.WAIT){
            if(Gdx.input.isKeyPressed(62)){
                miniGState = MinigameState.START;
                startTime = Instant.now();
            }
        }
        else if(miniGState == MinigameState.START){
            if(gameTime.getEpochSecond() - startTime.getEpochSecond() >= GAME_LENGTH_SECONDS){
                miniGState = MinigameState.END;
            }
            mouse.Update(delta);
        }
        else if(miniGState == MinigameState.END){

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
