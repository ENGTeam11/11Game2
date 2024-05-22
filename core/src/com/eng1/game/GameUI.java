package com.eng1.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class GameUI {
    private Stage stage;
    private Skin skin;
    private Dialog controlsDialog;
    private Label energyLabel, timeLabel, scoreLabel, dayLabel, studyLabel, relaxLabel, eatLabel, walkLabel;
    private Table statsTable, streaksTable;

    public GameUI(Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.skin = skin;
        setupStatsDialog();
        setupStreaksDialog();
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
    private void setupStatsDialog() {
        Table wrapperTable = new Table();
        wrapperTable.setFillParent(true);
        wrapperTable.top().right();

        statsTable = new Table(skin);
        float tableWidth = Gdx.graphics.getWidth() * 0.20f;
        float tableHeight = Gdx.graphics.getHeight() * 0.20f;

        statsTable.setBackground(skin.getDrawable("default-pane"));
        statsTable.pad(10);

        timeLabel = new Label("Time: " + GameStats.getFormattedTime(), skin);
        dayLabel = new Label("Day: " + GameStats.getDay(), skin);
        scoreLabel = new Label("Score: " + GameStats.getScore(), skin);
        energyLabel = new Label("Energy: " + GameStats.getEnergy(), skin );
        

        statsTable.add(dayLabel).expandX().fillX().row();
        statsTable.add(timeLabel).expandX().fillX().row();
        statsTable.add(scoreLabel).expandX().fillX().row();
        statsTable.add(energyLabel).expand().fillX().row();
        

        statsTable.setSize(tableWidth, tableHeight);
        wrapperTable.add(statsTable);
        stage.addActor(wrapperTable);
    }

    private void setupStreaksDialog() {
        Table streakWrapperTable = new Table();
        streakWrapperTable.setFillParent(true);
        streakWrapperTable.top().left();

        streaksTable = new Table(skin);
        float tableWidth = Gdx.graphics.getWidth() * 0.20f;
        float tableHeight = Gdx.graphics.getHeight() * 0.20f;

        statsTable.setBackground(skin.getDrawable("default-pane"));
        statsTable.pad(10);

        streaksTable.setBackground(skin.getDrawable("default-pane"));
        streaksTable.pad(10);

        studyLabel = new Label("Studious: " + GameStats.getStreak("Studious"), skin);
        relaxLabel = new Label("Relaxed: " + GameStats.getStreak("Relaxed"), skin);
        eatLabel = new Label("Well Fed: " + GameStats.getStreak("Well Fed"), skin);
        walkLabel = new Label("Walk Bonus: " + walkstatus(), skin);

        streaksTable.add("Current streaks").expandX().fillX().row();
        streaksTable.add(studyLabel).expandX().fillX().row();
        streaksTable.add(relaxLabel).expandX().fillX().row();
        streaksTable.add(eatLabel).expandX().fillX().row();
        streaksTable.add(walkLabel).expandX().fillX().row();

        streaksTable.setSize(tableWidth, tableHeight);
        streakWrapperTable.add(streaksTable);
        stage.addActor(streakWrapperTable);
    }

    public void updateStats(){
        dayLabel.setText("Day: " + GameStats.getDay());
        timeLabel.setText("Time: " + GameStats.getFormattedTime());
        scoreLabel.setText("Score: " + GameStats.getScore());
        energyLabel.setText("Energy: " + GameStats.getEnergy());
        


    }

    public void updateStreakUI(){
        studyLabel.setText("Studious: " + GameStats.getStreak("Study"));
        relaxLabel.setText("Relaxed: " + GameStats.getStreak("Relax"));
        eatLabel.setText("Well Fed: " + GameStats.getStreak("Eat"));
        walkLabel.setText("Walk Bonus: " + walkstatus());
    }

    private String walkstatus(){
        if (GameStats.getWalked()){
            return "Active";
        }
        return "Not active";
    }

    public void render(float delta) {
        updateStats();
        updateStreakUI();
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
