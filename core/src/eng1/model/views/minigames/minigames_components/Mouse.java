package eng1.model.views.minigames.minigames_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Mouse {
    private Vector2 mousePos;
    private CircleBounds circleBounds;
    public Mouse(){
        mousePos = new Vector2();
        circleBounds = new CircleBounds(mousePos, 5);
    }

    public void Update(float delta){
        mousePos.set(Gdx.input.getX(),Gdx.input.getY());
        circleBounds.setPosition(mousePos);
    }

    public CircleBounds getCircleBounds(){
        return circleBounds;
    }
    
    public Vector2 getPos(){
        return mousePos;
    }
}
