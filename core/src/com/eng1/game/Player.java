package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Player extends Sprite implements InputProcessor {
    private Vector2 velocity = new Vector2();
    private float speed = 300; // Speed
    private float scale;
    private boolean up, down, left, right;
    private PlayerTracker playerTracker;
    private boolean interacting = false;
    private Play playScreen;

    // Animations
    private float stateTime; //time since last animation frame change
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> runRight;
    private Animation<TextureRegion> runLeft;
    private Animation<TextureRegion> runUp;
    private Animation<TextureRegion> runDown;
    private static final float FRAME_TIME = 1 / 5f; //5 fps

    public Player(){

    }

    public Player(Play playScreen, PlayerTracker playerTracker, MapManager mapManager) {
        this.playScreen = playScreen;
        this.setScale(3.0f);
        scale = mapManager.getCurrentScale();
        this.playerTracker = playerTracker;
        Vector2 spawnPoint = mapManager.findObjectPosition("spawn_points", "spawn_point");
        if (spawnPoint != null) {
            setPosition(spawnPoint.x, spawnPoint.y);
        }

        String characterName = Play.getSelectedCharacter();

        // Load the texture atlas and create the animation
//        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("allAnimationsAtlas.atlas"));
//        Array<TextureAtlas.AtlasRegion> walkFrames = atlas.findRegions("left_walk");
//        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames, Animation.PlayMode.LOOP);

        // animations my self
        TextureAtlas allAnimations = new TextureAtlas(Gdx.files.internal("playerCharacters/allAnimationsAtlas.atlas"));

        idle = new Animation<>(FRAME_TIME, allAnimations.findRegions("down_idle"));
        idle.setPlayMode(Animation.PlayMode.LOOP);

        //run right TextureAtlas
        runRight = new Animation<>(FRAME_TIME, allAnimations.findRegions("right_walk"));
        runRight.setPlayMode(Animation.PlayMode.LOOP);

        //run left animation
        runLeft = new Animation<>(FRAME_TIME, allAnimations.findRegions("left_walk"));
        runLeft.setPlayMode(Animation.PlayMode.LOOP);

        //run up animation
        runUp = new Animation<>(FRAME_TIME, allAnimations.findRegions("up_walk"));
        runUp.setPlayMode(Animation.PlayMode.LOOP);

        //run down animation
        runDown = new Animation<>(FRAME_TIME, allAnimations.findRegions("down_walk"));
        runDown.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0f;
    }

    /**
     * updates player position according to movement and time since last update.
     * also checks if they are interacting with an activity
     * @param delta time since last render
     * @param mapManager the world map
     */
    public void update(float delta, MapManager mapManager) {
        stateTime += delta;

        //changing players position
        movementscheck();
        float newX = getX() + velocity.x * delta;
        if (mapManager.inRegion(new Vector2(newX, getY()), getWidth(), getHeight(), "collisions")) { // checks if player has hit a collision layer and prevents movement if so
            newX = getX();
        }
        float newY = getY() + velocity.y * delta;
        if (mapManager.inRegion(new Vector2(newX, newY), getWidth(), getHeight(), "collisions")) { // checks if player has hit a collision layer and prevents movement if so
            newY = getY();  
        }
        setPosition(newX, newY);

        //checking for activity interactions
        if (interacting){
            if (mapManager.inRegion(new Vector2(newX, newY), getWidth(), getHeight(), "activities")){
                interacting = false;
                Activity.completeActivity(playerTracker.checkPlayerActivity(new Vector2(getX(), getY()), getWidth(), getHeight()));
            }
        }

        //checks if player is standing on a map transfer tile
        playScreen.showInteractPrompt = mapManager.inRegion(new Vector2(newX, newY), getWidth(), getHeight(), "activities");
        if (playerTracker != null) {
            playerTracker.checkPlayerTile(newX, newY);
        }

        //scales player according to map scale
        scale = mapManager.getCurrentScale();
        this.setScale(3.0f / scale);
    }

    
    /**
     * checks what the players movement should be based on input keys
     */
    public void movementscheck(){
        if(up && !down){
            velocity.y = speed / scale;
        }
        else if(!up && down){
            velocity.y = -speed / scale;
        }
        else{
            velocity.y = 0;
        }

        if(!left && right){
            velocity.x = speed / scale;
        }
        else if(left && !right){
            velocity.x = -speed / scale;
        }
        else{
            velocity.x = 0;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                up = true;
                break;
            case Keys.S:
                down = true;
                break;
            case Keys.A:
                left = true;
                break;
            case Keys.D:
                right = true;
                break;
                case Keys.E:
                case Keys.ENTER:
                case Keys.SPACE:
                    interacting = true;
                    break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                up = false;
                break;
            case Keys.S:
                down = false;
                break;
            case Keys.A:
                left = false;
                break;
            case Keys.D:
                right = false;
                break;
            case Keys.E:
            case Keys.ENTER:
            case Keys.SPACE:
                interacting = false;
                break;

        }
        return true;
    }

    @Override
    /**
     * draws the player on screen
     * @param batch the drawing batch for the render function calling this one
     */
    public void draw(Batch batch){
        Animation<TextureRegion> animation = idle;
        if (up){
            animation = runUp;
        }
        if (down){
            animation = runDown;
        }
        if (left){
            animation = runLeft;
        }
        if (right){
            animation = runRight;
        }

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), currentFrame.getRegionWidth() * getScaleX(), currentFrame.getRegionHeight() * getScaleY());
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
