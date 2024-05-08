package minigames.minigames_components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Obstacle{
    private Texture bulletTexture;

    public Bullet(Texture inBulletTexture, Vector2 inBulletPositions,Circle inBulletBounds){
        super();
        bulletTexture = inBulletTexture;
        obstaclePos = inBulletPositions;
        obstacleBounds = inBulletBounds;
        speed = 40;
        getBulletTextureRegion();
    }
    private void getBulletTextureRegion(){
        obstacleTexture = new TextureRegion(bulletTexture,0,14*16,16,16);
    }
    public void update(float deltaTime){
        checkObstacleIsWithinScreen();
        move(deltaTime);
        Circle newBulletBoundsPos = new Circle(obstaclePos.x + bulletTexture.getWidth()/2,obstaclePos.y + bulletTexture.getHeight()/2 ,bulletTexture.getWidth()/2);
        obstacleBounds = newBulletBoundsPos;
    }

    private void move(float deltaTime){
        moveTrajectory = getNormalizedVectorDirection(obstaclePos, moveTrajectory);
        Vector2 moveBy = moveTrajectory.add(speed, speed);
        moveBy = moveBy.scl(deltaTime);
        obstaclePos = obstaclePos.add(moveBy);
    }

    private Vector2 getNormalizedVectorDirection(Vector2 playerPos, Vector2 mousePos){
        float directionX = mousePos.x - playerPos.x;
        float directionY = mousePos.y - playerPos.y;
        Vector2 directionVector = new Vector2(directionX,directionY);
        return directionVector.nor();
    }

}
