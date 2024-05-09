package minigames.minigames_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Mouse {
    private Vector2 mousePos;
    private Circle circleBounds;
    public Mouse(){
        mousePos = new Vector2();  
        circleBounds = new Circle(mousePos, 16);
    }

    public void update(float delta){
        //updates the mouse pos and its bounds
        mousePos.set(Gdx.input.getX(),Gdx.input.getY());
        float circlePosY = Gdx.graphics.getBackBufferHeight()-mousePos.y;
        Vector2 circlePosition = new Vector2(mousePos.x,circlePosY);
        circleBounds.setPosition(circlePosition);
    }

    public Circle getCircleBounds(){
        return circleBounds;
    }
    
    public Vector2 getPos(){
        return mousePos;
    }
}
