package minigames.minigames_components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private Vector2 obstaclePos;
    private Texture obstacleTexture;
    private float speed = 2;
    private double gravity = 0.4;
    private CircleBounds obstacleBounds;
    private boolean draw = true;
    private Vector2 moveTrajectory;
    private int angle =-1;

    public Obstacle(Texture inTexture, Vector2 inPosition){
        obstaclePos = inPosition;
        obstacleTexture = inTexture;
        obstacleBounds = new CircleBounds(obstaclePos, (float)obstacleTexture.getWidth()/2);
     }

    public Obstacle(Texture inTexture, Vector2 inPosition,int inAngle){
       obstaclePos = inPosition;
       obstacleTexture = inTexture;
       angle = inAngle;
       obstacleBounds = new CircleBounds(obstaclePos, (float)obstacleTexture.getWidth()/2);
    }

    public void Update(float delta){
        if(angle >= 25){
            CalcTrajectory(delta);
        }
    }

    public void CalcTrajectory(float delta){
        double xVel = (speed*delta) * Math.cos(angle);
        double yVel = (speed*delta)*Math.sin(angle) - (1/2)*gravity*delta*delta;
        moveTrajectory = new Vector2((float)xVel,(float)yVel);
    }

    public void Move(){
        obstaclePos = new Vector2(obstaclePos.x - moveTrajectory.x,obstaclePos.y - moveTrajectory.y);
        obstacleBounds.setPosition(obstaclePos);
    }

    public void Draw(SpriteBatch spriteBatch){
        spriteBatch.draw(obstacleTexture,obstaclePos.x,obstaclePos.y);
    }
    
    public Vector2 getPos(){
        return obstaclePos;
    }

    public CircleBounds getBounds(){
        return obstacleBounds;
    }

    public boolean getDraw(){
        return draw;
    }

    public void setDraw(boolean value){
        draw = value;
    }
}
