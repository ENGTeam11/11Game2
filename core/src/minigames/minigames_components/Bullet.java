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
        obstacleTexture = new TextureRegion(bulletTexture,0,13*16,16,16);
    }

    @Override
    public void update(float deltaTime){
        checkObstacleIsWithinScreen();
        move(deltaTime);
        Circle newBulletBoundsPos = new Circle(obstaclePos.x + obstacleTexture.getRegionWidth()/2,obstaclePos.y + obstacleTexture.getRegionHeight()/2 ,obstacleTexture.getRegionWidth()/2);
        obstacleBounds = newBulletBoundsPos;
    }

    private void move(float deltaTime){
        Vector2 moveBy = new Vector2(moveTrajectory.x *(speed * deltaTime), moveTrajectory.y * (speed * deltaTime));
        obstaclePos = new Vector2(obstaclePos.x + moveBy.x , obstaclePos.y + moveBy.y);
    }

    public void setMoveTrajectory(Vector2 value){
        moveTrajectory = value;
    }

}
