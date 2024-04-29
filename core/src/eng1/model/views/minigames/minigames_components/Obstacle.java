package eng1.model.views.minigames.minigames_components;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private Vector2 obstaclePos;
    private float speed = 2;
    private CircleBounds obstacleBounds;

    public Obstacle(Vector2 inPosition,float radius){
       obstaclePos = inPosition;
       obstacleBounds = new CircleBounds(obstaclePos, radius);
    }

    public void Update(float delta){

    }
    
    public Vector2 getPos(){
        return obstaclePos;
    }
}
