package minigames.minigames_components;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.eng1.game.Player;

public class MiniGamePlayer extends Player {
    private Texture playerTexture;
    private float playerSpeed;
    private Circle playerBounds;
    private Vector2 playerPosition;
    private ArrayList<Bullet> bullets;
    private final float SHOOT_DElAY = 0.3f;
    private float totalDelta;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private boolean academicWeaponInstance = false;

    public MiniGamePlayer(){
        super();
        bullets = new ArrayList<Bullet>();
    }

    public MiniGamePlayer(Texture inPlayerTexture, Vector2 inPlayerPosition, Circle inPlayerBounds){

    }

    public void update(float delta, SpriteBatch spriteBatch){
        totalDelta+=delta;
        processInput();
    }
    public void updateBullets(float delta,SpriteBatch spriteBatch){
        if(bullets.size() > 0){
            for(Iterator<Bullet> i = bullets.iterator(); i.hasNext();){
                Bullet bulletToUpdate = i.next();
                bulletToUpdate.update(delta);
                bulletToUpdate.draw(spriteBatch);
                if
            }
        } 
    }

    @Override
    public boolean keyDown(int keyCode){
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

    public void processInput(){
        if(moveUp){
            playerPosition = playerPosition.add(0, playerSpeed);
        }
        if(moveLeft){
            playerPosition = playerPosition.add(-playerSpeed,0);
        }
        if(moveRight){
            playerPosition = playerPosition.add(playerSpeed, 0);
        }
        if(moveDown){
            playerPosition = playerPosition.add(0,-playerSpeed);
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(totalDelta > SHOOT_DElAY){
                shoot();
                totalDelta = 0;
            }
        }
    }

    private void shoot() {
        Texture bulletTexture = null;
        Vector2 bulletPosition = playerPosition.add(playerTexture.getWidth()/2,playerTexture.getHeight());
        Vector2 bulletBoundPosition = new Vector2(bulletPosition.x + bulletTexture.getWidth()/2,bulletPosition.y + bulletTexture.getHeight()/2);
        Circle bulletBounds = new Circle(bulletBoundPosition,bulletTexture.getWidth()/2);
        bullets.add(new Bullet(bulletTexture,bulletPosition,bulletBounds));
    }

    public void Draw(SpriteBatch spriteBatch){
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

}
