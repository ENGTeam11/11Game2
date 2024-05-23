package minigames.minigames_components;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.eng1.game.Player;

public class MiniGamePlayer extends Player {
    private Texture playerTexture;
    private float playerSpeed;
    private Circle playerBounds;
    private Vector2 playerPosition;
    private ArrayList<Bullet> bullets;
    private final float SHOOT_DELAY = 0.3f;
    private float totalDelta;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private boolean academicWeaponInstance = false;

    public MiniGamePlayer(){
        super();
        bullets = new ArrayList<Bullet>();
    }

    public MiniGamePlayer(Texture inPlayerTexture, Vector2 inPlayerPosition, Circle inPlayerBounds){
        super();
        bullets = new ArrayList<Bullet>();
        playerSpeed = 50;
        playerTexture = inPlayerTexture;
        playerPosition = inPlayerPosition;
        playerBounds = inPlayerBounds;

    }

    public void update(float delta,SpriteBatch spriteBatch,ArrayList<Obstacle> obstacles){
        //updates the game logic
        totalDelta+=delta;
        processInput(delta);
        checkPlayerCollisions();
        updateBullets(delta, spriteBatch,obstacles);
    }

    private void checkBulletCollisions(Bullet bullet ,ArrayList<Obstacle> obstacles){
        //loops through each obstacle and checks if the bullet has hit a particular obstacle
        for(Obstacle obstacle : obstacles){
            if(Intersector.overlaps(bullet.getBounds(),obstacle.getBounds())){
                obstacle.setDraw(false);
                bullet.setDraw(false);
            }
        }
    }

    private void updateBullets(float delta,SpriteBatch spriteBatch,ArrayList<Obstacle> obstacles){
        //checks if there is any bullets in the list if not skip this function
        if(bullets.size() > 0){
            //loop to update the bullets
            for(Iterator<Bullet> i = bullets.iterator(); i.hasNext();){
                Bullet bulletToUpdate = i.next();
                if(bulletToUpdate.getDraw()){
                    bulletToUpdate.update(delta);
                    bulletToUpdate.draw(spriteBatch);
                }
                //checks if the bullets have collided with any obstacles
                checkBulletCollisions(bulletToUpdate, obstacles);
                if(!bulletToUpdate.getDraw()){
                    i.remove();
                }
            }
        } 
    }

    private void checkPlayerCollisions(){
        //checks if the player is trying to get outside the screen bounds

        if(playerPosition.x < 0){
            moveLeft = false;
        }
        if(playerPosition.y < 0){
            moveDown = false;
        }
        if(playerPosition.x + playerTexture.getWidth() >= Gdx.graphics.getWidth()){
            moveRight = false;
        }
        if(playerPosition.y + playerTexture.getHeight() >= Gdx.graphics.getHeight()){
            moveUp = false;
        }
    }

    @Override
    public boolean keyDown(int keyCode){
        //detects which key has been pressed and flags the movement booleans
        switch(keyCode){
            case Keys.W:
            case Keys.UP:
                moveUp = true;
                break;
            case Keys.A:
            case Keys.LEFT:
                moveLeft = true;
                break;
            case Keys.S:
            case Keys.DOWN:
                moveDown = true;
                break;
            case Keys.D:
            case Keys.RIGHT:
                moveRight = true;
                break;
        }
        return true;
    }
    
    @Override
    public boolean keyUp(int keyCode){
        //detects when the player stopped pressing the key
        switch(keyCode){
            case Keys.W:
            case Keys.UP:
                moveUp = false;
                break;
            case Keys.A:
            case Keys.LEFT:
                moveLeft = false;
                break;
            case Keys.S:
            case Keys.DOWN:
                moveDown = false;
                break;
            case Keys.D:
            case Keys.RIGHT:
                moveRight = false;
                break;
        }
        return true; 
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        //detects when the player pressed the left mouse button
        if(button == Input.Buttons.LEFT && academicWeaponInstance){
            if(totalDelta >SHOOT_DELAY){
                shoot(new Vector2(x, y));
            }
        }
        return true;
    }

    public void processInput(float delta){
        //responsible for making the player move 
        if(moveUp){
            playerPosition = playerPosition.add(0, playerSpeed*delta);
        }
        if(moveLeft){
            playerPosition = playerPosition.add(-playerSpeed*delta,0);
        }
        if(moveRight){
            playerPosition = playerPosition.add(playerSpeed*delta, 0);
        }
        if(moveDown){
            playerPosition = playerPosition.add(0,-playerSpeed*delta);
        }
    }

    private void shoot(Vector2 mousePos) {
        //initializes a bullet and adds it to the bullet list

        Texture bulletTexture = new Texture(Gdx.files.internal("minigame/Bullets.png"));
        Vector2 bulletPosition = new Vector2(playerPosition.x + playerTexture.getWidth()/2,playerPosition.y + playerTexture.getHeight() );
        Vector2 bulletBoundPosition = new Vector2(bulletPosition.x + bulletTexture.getWidth()/2,bulletPosition.y + bulletTexture.getHeight()/2);
        Circle bulletBounds = new Circle(bulletBoundPosition,bulletTexture.getWidth()/2);
        Bullet bulletToAdd = new Bullet(bulletTexture,bulletPosition,bulletBounds);
        Vector2 translatedMousePos = new Vector2(mousePos.x,Gdx.graphics.getHeight()-mousePos.y);
        bulletToAdd.setMoveTrajectory(bulletToAdd.getNormalizedVectorDirection(playerPosition, translatedMousePos));
        bullets.add(bulletToAdd);
    }

    public void draw(SpriteBatch spriteBatch){
        //draws the player
        spriteBatch.draw(playerTexture, playerPosition.x, playerPosition.y);
    }
    public Texture getTexture(){
        return playerTexture;
    }
    public Circle getBounds(){
        return playerBounds;
    }

    public Vector2 getPosition(){
        return playerPosition;
    }
    
    public void setGameInstance(boolean value){
        academicWeaponInstance = value;
    }

    public void cleanBullets(){
        bullets = new ArrayList<Bullet>();
    }

}
