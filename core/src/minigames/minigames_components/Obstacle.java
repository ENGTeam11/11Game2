package minigames.minigames_components;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    protected Vector2 obstaclePos;
    protected TextureRegion obstacleTexture;
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

    public void update(float delta){
        //takes care of the obstacle logic
        //checks if the obstacle is within the visible screen
        checkObstacleIsWithinScreen();
        if(angle >= 25){
            //calculates the traveling trajectory
            calcTrajectory(delta);
        }

        //updates the obstacle circular bound position
        Vector2 boundsPos = new Vector2(obstaclePos.x + obstacleTexture.getRegionWidth()/2,obstaclePos.y + obstacleTexture.getRegionHeight()/2);
        obstacleBounds.setPosition(boundsPos);
    }

    public void follow(Vector2 player,float deltaTime){
        //for the academic weapon game so the obstacles walk towards the player
        moveTrajectory = getNormalizedVectorDirection(obstaclePos, player);
        Vector2 moveBy = new Vector2(moveTrajectory.x+(speed*deltaTime),moveTrajectory.y+(speed*deltaTime));
        obstaclePos = new Vector2(obstaclePos.x + moveBy.x,obstaclePos.y + moveBy.y);
        obstacleBounds = new Circle(obstaclePos.x+obstacleTexture.getRegionWidth()/2,obstaclePos.y+obstacleTexture.getRegionHeight()/2,obstacleTexture.getRegionWidth()/2);
    }
    
    public Vector2 getNormalizedVectorDirection(Vector2 playerPos, Vector2 mousePos){
        //for finding a normalized direction vector between 2 points on a plane
        float directionX = mousePos.x - playerPos.x;
        float directionY = mousePos.y - playerPos.y;
        Vector2 directionVector = new Vector2(directionX,directionY);
        return directionVector.nor();
    }

    protected void checkObstacleIsWithinScreen(){
        //checks if the obstacle is within the screen bounds
        if(getPos().y  < 0){
            setDraw(false);
        }
        if(getPos().y + getTexture().getRegionHeight() >= Gdx.graphics.getHeight()){
            setDraw(false);
        }
        if(getPos().x < 0){
            setDraw(false);
        }
        if(getPos().x + getTexture().getRegionWidth() >= Gdx.graphics.getWidth()){
            setDraw(false);
        }
    }

    private void calcTrajectory(float delta){
        //calculates the movement trajectory for food ninja minigame
        double xVel = (speed*delta) * Math.cos(angle);
        double yVel = (speed*delta) * Math.sin(angle) - (1/2)*gravity*delta*delta;
        moveTrajectory = new Vector2((float)xVel,(float)yVel);
    }

    public void move(){
        //makes the obstacle move
        obstaclePos = new Vector2(obstaclePos.x - moveTrajectory.x,obstaclePos.y - moveTrajectory.y);
    }

    public void draw(SpriteBatch spriteBatch){
        //draws the obstacle
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
