package minigames.minigames_components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private Vector2 obstaclePos;
    private Texture obstacleTexture;
    private float speed = 2;
    private float gravity = 0.4
    private CircleBounds obstacleBounds;
    private boolean draw = true;
    private Vector2 moveTrajectory;

    public Obstacle(Texture inTexture, Vector2 inPosition,float radius){
       obstaclePos = inPosition;
       obstacleTexture = inTexture;
       obstacleBounds = new CircleBounds(obstaclePos, radius);
    }

    public void Update(float delta){

    }

    public void CalcTrajectory(float delta, float angle){
        Vector2 xVel = (speed*delta) * Math.cos(angle);
        Vector2 yVel = (speed*delta)*Math.sin(angle) - (1/2)*gravity*delta*delta
        moveTrajectory = new Vector2(xVel,yVel);
    }

    public void Move(){
        obstaclePos = obstaclePos - moveTrajectory;
        obstacleBounds.setPosition(obstaclePos);
    }

    public void Draw(SpriteBatch spriteBatch){
        spriteBatch.draw(obstacleTexture,obstaclePos.x,obstaclePos.y);
    }
    
    public Vector2 getPos(){
        return obstaclePos;
    }

    public boolean getDraw(){
        return draw;
    }

    public void setDraw(boolean value){
        draw = value;
    }
}
