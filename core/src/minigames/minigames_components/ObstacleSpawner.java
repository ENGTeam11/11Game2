package minigames.minigames_components;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ObstacleSpawner {
    private final int INIT_OBSTACLES = 10;
    private final int OBSTAClE_LIMIT = 100;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<TextureRegion> obstacleTextures;
    private Texture obstacleSpriteSheet;

    public ObstacleSpawner(Texture inObstacleTexture){
        obstacleSpriteSheet = inObstacleTexture;
        obstacles = new ArrayList<Obstacle>();
    }

    public void SpawnFoodNinjaObstacles(){
        Random generator = new Random();
        for(int i =0; i < INIT_OBSTACLES;i++){
            if(obstacles.size() >= OBSTAClE_LIMIT) break;
            int X = generator.nextInt(Gdx.graphics.getWidth());
            int angle = generator.nextInt((90-25)+1)+25;
            int texturePicked = generator.nextInt(obstacleTextures.size());
            Obstacle obstacle = new Obstacle(obstacleTextures.get(texturePicked),new Vector2(X,0),angle);
            obstacles.add(obstacle);
        }
    }

    public void SplitFoodTextures(){
        for(int i = 0; i < obstacleSpriteSheet.getWidth()/16; i++){
            obstacleTextures.add(new TextureRegion(obstacleSpriteSheet,i*16,0,16,16));
        }
    }

    public void ClearObstacles(){
        obstacles = new ArrayList<Obstacle>();
    }

    public void RemoveObstacle(){
        for(Obstacle obstacle : obstacles){
            if(!obstacle.getDraw()){
                obstacles.remove(obstacle);
            }
        }
    }

    public void Draw(SpriteBatch spriteBatch){
        for(Obstacle obstacle : obstacles){
            if(obstacle.getDraw()) obstacle.Draw(spriteBatch);
        }
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }
}
