package com.eng1.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class GameUI {
    private Stage stage;
    private Skin skin;
    private Dialog controlsDialog;

    public GameUI(Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.skin = skin;

        setupControlsDialog();
    }

    private void setupControlsDialog() {
        controlsDialog = new Dialog("HOW TO PLAY", skin);
        controlsDialog.text("Use WASD to move around and E to interact!");
        controlsDialog.getTitleLabel().setAlignment(Align.center);
        controlsDialog.button("OK",true);
        controlsDialog.setMovable(false);
        controlsDialog.show(stage);
        controlsDialog.setSize(stage.getWidth()  * 0.25f, stage.getHeight() * 0.25f);
        controlsDialog.setPosition((stage.getWidth() - controlsDialog.getWidth()) / 2, (stage.getHeight() - controlsDialog.getHeight()) / 2);

    }
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public Stage getStage(){
        return stage;
    }
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }

}