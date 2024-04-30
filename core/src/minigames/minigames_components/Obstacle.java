package minigames.minigames_components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private Vector2 obstaclePos;
    private Texture obstacleTexture;
    private float speed = 2;
    private CircleBounds obstacleBounds;
    private boolean draw = true;

    public Obstacle(Texture inTexture, Vector2 inPosition,float radius){
       obstaclePos = inPosition;
       obstacleTexture = inTexture;
       obstacleBounds = new CircleBounds(obstaclePos, radius);
    }

    public void Update(float delta){

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
