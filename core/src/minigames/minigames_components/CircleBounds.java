package minigames.minigames_components;

import com.badlogic.gdx.math.Vector2;

public class CircleBounds {
    private Vector2 boundPos;
    private float boundRad;

    public CircleBounds(Vector2 inBoundPos,float inBoundRad){
        boundPos = inBoundPos;
        boundRad = inBoundRad;
    }

    public boolean Contains(CircleBounds circleContained){
        float xDiff = getPosition().x - circleContained.getPosition().x;
        float yDiff = getPosition().y - circleContained.getPosition().y;

        return Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2)) <= getRadius();
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
