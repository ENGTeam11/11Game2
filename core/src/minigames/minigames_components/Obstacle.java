package minigames.minigames_components;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    protected Vector2 obstaclePos;
    private TextureRegion obstacleTexture;
    protected float speed = 20;
    protected double gravity = 4.5;
    protected Circle obstacleBounds;
    protected boolean draw = true;
    protected Vector2 moveTrajectory;
    protected int angle =-1;

    public Obstacle(){

    }

    public Obstacle(TextureRegion inTexture, Vector2 inPosition){
        obstaclePos = inPosition;
        obstacleTexture = inTexture;
        obstacleBounds = new Circle(obstaclePos, (float)obstacleTexture.getRegionWidth()/2);
    }

    public Obstacle(TextureRegion inTexture, Vector2 inPosition,int inAngle){
       obstaclePos = inPosition;
       obstacleTexture = inTexture;
       angle = inAngle;
       obstacleBounds = new Circle(obstaclePos, (float)obstacleTexture.getRegionWidth()/2);
    }

    public void Update(float delta){
        checkObstacleIsWithinScreen();
        if(angle >= 25){
            CalcTrajectory(delta);
        }
        Vector2 boundsPos = new Vector2(obstaclePos.x + obstacleTexture.getRegionWidth()/2,obstaclePos.y + obstacleTexture.getRegionHeight()/2);
        obstacleBounds.setPosition(boundsPos);
    }

    protected void checkObstacleIsWithinScreen(){
        if(getPos().y  < 0){
            setDraw(false);
        }
        if(getPos().y + getTexture().getRegionHeight()*2 >= Gdx.graphics.getBackBufferHeight()){
            setDraw(false);
        }
        if(getPos().x < 0){
            setDraw(false);
        }
        if(getPos().x + getTexture().getRegionWidth()*2 >= Gdx.graphics.getBackBufferWidth()){
            setDraw(false);
        }
    }

    private void CalcTrajectory(float delta){
        double xVel = (speed*delta) * Math.cos(angle);
        double yVel = (speed*delta) * Math.sin(angle) - (1/2)*gravity*delta*delta;
        moveTrajectory = new Vector2((float)xVel,(float)yVel);
    }

    public void Move(){
        obstaclePos = new Vector2(obstaclePos.x - moveTrajectory.x,obstaclePos.y - moveTrajectory.y);
    }

    public void Draw(SpriteBatch spriteBatch){
        spriteBatch.draw(obstacleTexture,obstaclePos.x,obstaclePos.y);
    }
    
    public Vector2 getPos(){
        return obstaclePos;
    }

    public Circle getBounds(){
        return obstacleBounds;
    }

    public boolean getDraw(){
        return draw;
    }

    public TextureRegion getTexture(){
        return obstacleTexture;
    }

    public void setDraw(boolean value){
        draw = value;
    }
}
