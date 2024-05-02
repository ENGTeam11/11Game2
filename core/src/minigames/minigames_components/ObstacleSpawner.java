package minigames.minigames_components;

import java.util.ArrayList;
import java.util;
import com.badlogic.gdx.Gdx;

public class ObstacleSpawner {
    private final int INIT_OBSTACLES = 10;
    private ArrayList<Obstacle> obstacles;
    private Texture obstacleTexture;

    public ObstacleSpawner(Texture inObstacleTexture){
        obstacleTexture = inObstacleTexture;
    }

    public void InitFoodNinjaObstacles(){
        Random generator = new Random();
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        for(int i =0; i < INIT_OBSTACLES;i++){
            int X = generator.nextInt(Gdx.graphics.getWidth())
            Obstacle obstacle = new Obstacle(obstacleTexture,new Vector2(X,0),5);
            obstacles.add();
        }
    }

    public void Draw(SpriteBatch spriteBatch){
        for(Obstacle obstacle : obstacles){
            if(obstacle.getDraw()):
                obstacle.Draw(spriteBatch);
        }
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }
}
