package eng1.model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.LeaderboardEntry;
import com.eng1.game.LeaderboardManager;
import com.eng1.game.MenuState;

import javax.swing.event.ChangeEvent;

public class LeaderboardScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private List<LeaderboardEntry> leaderboardList;
    private ScrollPane scrollPane;
    private HeslingtonHustle parent;

    public LeaderboardScreen(HeslingtonHustle parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        java.util.List<LeaderboardEntry> leaderboardEntries = LeaderboardManager.loadScores();
        Array<LeaderboardEntry> entriesArray = new Array<>();
        for (LeaderboardEntry entry : leaderboardEntries) {
            entriesArray.add(entry);
        }

        leaderboardList = new List<>(skin);
        leaderboardList.setItems(entriesArray);

        scrollPane = new ScrollPane(leaderboardList, skin);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setFillParent(true);

        Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MenuState.MENU);
            }
        });

        table.add(scrollPane).expandX().fill();
        table.row();
        table.add(backButton).pad(10);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parent.changeScreen(MenuState.MENU);
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
