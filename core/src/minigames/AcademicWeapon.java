package minigames;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.MenuState;
import com.eng1.game.Player;

import minigames.minigames_components.MiniGamePlayer;
import minigames.minigames_components.MinigameState;
import minigames.minigames_components.Mouse;
import minigames.minigames_components.Obstacle;
import minigames.minigames_components.ObstacleSpawner;

public class AcademicWeapon implements Screen{
    //game = player shooting at letters 
    private HeslingtonHustle parent;
    private ObstacleSpawner obstacleManager;
    private MinigameState miniGState;
    private Instant startTime;
    private Stage canvas;
    private TextArea screenText,timer;
    private Mouse mouse;
    private MiniGamePlayer player;
    private final int GAME_LENGTH_SECONDS = 30;  

    public AcademicWeapon(HeslingtonHustle inParent){
        parent = inParent;
        obstacleManager = new ObstacleSpawner(new Texture(Gdx.files.internal("minigame/LetterFont.png")));
        obstacleManager.splitLettertextures();
        player = initialisePlayer();
        player.setGameInstance(true);
        mouse = new Mouse();
    }

    private MiniGamePlayer initialisePlayer(){
        Texture playerTexture = new Texture(Gdx.files.internal("minigame/MiniGamePlayer.png")) ;
        Vector2 playerPosition = new Vector2(Gdx.graphics.getBackBufferWidth()/2-playerTexture.getWidth()/2,Gdx.graphics.getBackBufferHeight()/2-playerTexture.getHeight()/2);
        Circle playerBounds = new Circle(playerPosition, playerTexture.getWidth()/2);
        return new MiniGamePlayer(playerTexture,playerPosition,playerBounds);
    }

    @Override
    public void show() {
        //Sets up the stage the game will be rendered on
        canvas = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        //Sets the style for the text areas
        TextField.TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.font = new BitmapFont();
        
        //Initializes the text area screen anouncements will be made on
        screenText = new TextArea("",textFieldStyle);
        screenText.setSize(200,20);
        screenText.setPosition(Gdx.graphics.getWidth()/2-screenText.getWidth()*2, Gdx.graphics.getHeight()/2 - screenText.getHeight()*2);


        //Initializes the text area responsible for showing the game time
        timer = new TextArea("", textFieldStyle);
        timer.setSize(20,20);
        timer.setPosition(0, Gdx.graphics.getBackBufferHeight()-timer.getHeight()*2);

        //Adds the text areas to the screen
        canvas.addActor(screenText);
        canvas.addActor(timer);

        //Sets the minigame state to wait
        miniGState = MinigameState.WAIT;

        //Sets the input to the player 
        Gdx.input.setInputProcessor(player);
    }

    @Override 
    public void render(float delta) {
        //Clears the screen before drawing to it again
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Tells the batch to begin drawing
        canvas.getBatch().begin();

        //Gets the current time from the system clock
        Instant gameTime = Instant.now();

        //This if statement takes care of the state before the game begins
        if(miniGState == MinigameState.WAIT){ 

            //displays the instructions to continue for the player
            screenText.setText("Press space to start the game");

            //when space is pressed starts the minigame
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                miniGState = MinigameState.START;
                startTime = Instant.now();
                screenText.setText("");
            }
        }

        //This if statement takes care of mini game logic 
        else if(miniGState == MinigameState.START){
            //Calculates the game duration
            long gameDuration = gameTime.getEpochSecond() - startTime.getEpochSecond();

            /*checks the difference between the start time and end time of the game in seconds since 1970 and
            checks if the difference is longer than the set game length in seconds*/
            timer.setText(String.valueOf(GAME_LENGTH_SECONDS-(int)gameDuration));
             
            if(gameDuration >= GAME_LENGTH_SECONDS){
                miniGState = MinigameState.END;
            }
            update(delta,gameDuration);
        }
        //This if statement is responsible for handling the end of the game 
        else if(miniGState == MinigameState.END){
            screenText.setText("Press space to go back to the main game");
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
                obstacleManager.clearObstacles();
                miniGState = MinigameState.WAIT;
                parent.changeScreen(MenuState.APPLICATION);
            }
        }
        canvas.getBatch().end();
        canvas.draw();
        canvas.act();
    }

    public void update(float delta,long gameDuration){
        player.update(delta, (SpriteBatch)canvas.getBatch(),obstacleManager.getObstacles());
        player.draw((SpriteBatch)canvas.getBatch());
        mouse.update(delta);
        manageObstacles(gameDuration,delta);
    }

    public void manageObstacles(long gameDuration,float delta){
        if(gameDuration % 3 == 0){
            obstacleManager.spawnAcademicWeaponObstacles();
        }
        for(Obstacle obstacle : obstacleManager.getObstacles()){
            obstacle.follow(player.getPosition(), delta);
            obstacle.draw((SpriteBatch)canvas.getBatch());
        }
        obstacleManager.removeObstacle();
    }

    @Override
    public void resize(int width, int height) {
    
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
        dispose();
    }

    @Override
    public void dispose() {
        canvas.dispose();
        obstacleManager.clearObstacles();
    }
    
}
