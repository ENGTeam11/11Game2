package minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.MenuState;

import minigames.minigames_components.MinigameState;
import minigames.minigames_components.ObstacleSpawner;

public class AcademicWeapon implements Screen{
    //game = player shooting at letters 
    private HeslingtonHustle parent;
    private ObstacleSpawner obstacleManager;
    private MinigameState miniGState;
    private Instant startTime;
    private Stage canvas;
    private TextArea screenText,timer;
    private final int GAME_LENGTH_SECONDS = 30;  

    public AcademicWeapon(HeslingtonHustle inParent){
        parent = inParent;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
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
        }
        //This if statement is responsible for handling the end of the game 
        else if(miniGState == MinigameState.END){
            screenText.setText("Press space to go back to the main game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                obstacleManager.ClearObstacles();
                miniGState = MinigameState.WAIT;
                parent.changeScreen(MenuState.APPLICATION);
            }
        }
        canvas.getBatch().end();
        canvas.draw();
        canvas.act();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }
    
}
