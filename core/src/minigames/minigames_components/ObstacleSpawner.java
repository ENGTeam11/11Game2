package minigames.minigames_components;

import java.util.ArrayList;

public class ObstacleSpawner {
    private final int INIT_OBSTACLES = 10;
    private ArrayList<Obstacle> obstacles;

    public ObstacleSpawner(){

    }

    public void InitFoodNinjaObstacles(){
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        for(int i =0; i < INIT_OBSTACLES;i++){
            Obstacle obstacle = new Obstacle();
            obstacles.add();
        }
    }

    public void Update(){

    }
}
