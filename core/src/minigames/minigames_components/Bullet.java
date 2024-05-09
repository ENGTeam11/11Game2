package minigames.minigames_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Obstacle{
    private Texture bulletTexture;

    public Bullet(Texture inBulletTexture, Vector2 inBulletPositions,Circle inBulletBounds){
        super();
        bulletTexture = inBulletTexture;
        obstaclePos = inBulletPositions;
        obstacleBounds = inBulletBounds;
        speed = 200;
        getBulletTextureRegion();
    }

    private void getBulletTextureRegion(){
        //sets the bullet to a specific texture on the bullet sprite sheet
        obstacleTexture = new TextureRegion(bulletTexture,0,13*16,16,16);
    }

    @Override
    public void update(float deltaTime){
        //checks if the obstacle is within the screen
        checkObstacleIsWithinScreen();
        //responsible for making the obstacle move
        move(deltaTime);
        //moves the obstacle bounds as the obstacle moves
        Circle newBulletBoundsPos = new Circle(obstaclePos.x + obstacleTexture.getRegionWidth()/2,obstaclePos.y + obstacleTexture.getRegionHeight()/2 ,obstacleTexture.getRegionWidth()/2);
        obstacleBounds = newBulletBoundsPos;
    }

    private void move(float deltaTime){
        //uses the calculated moveTrajectory vector and multiplies it  by the speed and time
        Vector2 moveBy = new Vector2(moveTrajectory.x *(speed * deltaTime), moveTrajectory.y * (speed * deltaTime));
        obstaclePos = new Vector2(obstaclePos.x + moveBy.x , obstaclePos.y + moveBy.y);
    }

    public void setMoveTrajectory(Vector2 value){
        moveTrajectory = value;
    }

}
