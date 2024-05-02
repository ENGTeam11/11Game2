package minigames.minigames_components;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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
            int X = generator.nextInt(Gdx.graphics.getWidth());
            int angle = generator.nextInt((90-25)+1)+25;
            Obstacle obstacle = new Obstacle(obstacleTexture,new Vector2(X,0),5,angle);
            obstacles.add(obstacle);
        }
    }

    public void Spawn(int timeLeft){
        for(int i = 0; i < INIT_OBSTACLES*timeLeft;)
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
