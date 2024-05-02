package minigames.minigames_components;

import com.badlogic.gdx.math.Vector2;

public class CircleBounds {
    private Vector2 boundPos;
    private float boundRad;

    public CircleBounds(Vector2 inBoundPos,float inBoundRad){
        boundPos = inBoundPos;
        boundRad = inBoundRad;
    }

    public boolean Contains(CircleBounds circle1, CircleBounds circle2){
        float xDiff = circle1.getPosition().x - circle2.getPosition().x;
        float yDiff = circle1.getPosition().y - circle2.getPosition().y;

        return Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2)) <= circle1.getRadius();
    }

    public Vector2 getPosition(){
        return boundPos;
    }

    public void setPosition(Vector2 inPos){
        boundPos = inPos;
    }
    
    public float getRadius(){
        return boundRad;
    }



}
