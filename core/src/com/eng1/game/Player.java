package com.eng1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {
    private Vector2 velocity = new Vector2();
    private float speed = 300; // Speed
    private float scale;
    private PlayerTracker playerTracker;

    public Player(Sprite sprite, PlayerTracker playerTracker, MapManager mapManager) {
        super(sprite.getTexture());
        this.setScale(3.0f);
        scale = mapManager.getCurrentScale();
        this.playerTracker = playerTracker;
        Vector2 spawnPoint = mapManager.findObjectPosition("spawn_points", "spawn_point");
        if (spawnPoint != null) {
            setPosition(spawnPoint.x, spawnPoint.y);
        }
        Gdx.input.setInputProcessor(this);
    }
    public void update(float delta, MapManager mapManager) {
        float newX = getX() + velocity.x * delta;
        if (mapManager.inRegion(new Vector2(newX, getY()), getWidth(), getHeight(), "collisions")) {
            newX = getX();
        }
        float newY = getY() + velocity.y * delta;
        if (mapManager.inRegion(new Vector2(newX, newY), getWidth(), getHeight(), "collisions")) {
            newY = getY();  
        }
        setPosition(newX, newY);
        if (playerTracker != null) {
            playerTracker.checkPlayerTile(newX, newY);
        }
        
        scale = mapManager.getCurrentScale();
        this.setScale(3.0f / scale);

        System.out.println(getScaleX());
    }
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.UP:
                velocity.y = speed / scale;
                break;
            case Keys.S:
            case Keys.DOWN:
                velocity.y = -speed / scale;
                break;
            case Keys.A:
            case Keys.LEFT:
                velocity.x = -speed / scale;
                break;
            case Keys.D:
            case Keys.RIGHT:
                velocity.x = speed / scale;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.S:
            case Keys.UP:
            case Keys.DOWN:
                velocity.y = 0;
                break;
            case Keys.A:
            case Keys.D:
            case Keys.LEFT:
            case Keys.RIGHT:
                velocity.x = 0;
                break;
        }
        return true;
    }

    @Override
    public void draw(Batch batch){
        batch.draw(getTexture(), getX(), getY(), getTexture().getWidth()*getScaleX(), getTexture().getHeight()*getScaleY());
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
