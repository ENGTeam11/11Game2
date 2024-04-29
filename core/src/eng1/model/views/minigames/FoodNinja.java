package eng1.model.views.minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.eng1.game.Play;

import eng1.model.views.minigames.minigames_components.MinigameState;
import eng1.model.views.minigames.minigames_components.Mouse;

public class FoodNinja implements Screen {
    private Play gameState;
    private Mouse mouse;
    private MinigameState miniGState;
    private Instant startTime;
    public FoodNinja(Play game){
        gameState = game;
        miniGState = MinigameState.WAIT;
        mouse = new Mouse();
    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(new InputProcessor() {
            
        });
    }

    @Override
    public void render(float delta) {
        if(miniGState == MinigameState.WAIT){
            Gdx.input.
        }
        else if(miniGState == MinigameState.START){
            mouse.Update(delta);
        }
        else if(miniGState == MinigameState.END){

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
      
    }

    @Override
    public void dispose() {
       
    }
    
}
