package eng1.model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.LeaderboardEntry;
import com.eng1.game.LeaderboardManager;
import com.eng1.game.MenuState;

import java.util.Collections;

public class LeaderboardScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private List<String> leaderboardList;
    private ScrollPane scrollPane;
    private HeslingtonHustle parent;

    public LeaderboardScreen(HeslingtonHustle parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        leaderboardList = new List<>(skin);
        scrollPane = new ScrollPane(leaderboardList, skin);
        scrollPane.setSmoothScrolling(false);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(new Label("Leaderboard", skin)).center().padBottom(20);
        table.row();
        table.add(scrollPane).expand().fill().padBottom(20);
        table.row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MenuState.MENU);
            }
        });

        table.add(backButton).pad(10);

        stage.addActor(table);
    }

    private void loadLeaderboard() {
        java.util.List<LeaderboardEntry> leaderboardEntries = LeaderboardManager.loadScores();
        Collections.sort(leaderboardEntries, (e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));

        Array<String> entriesArray = new Array<>(); // Change to Array<String>
        int rank = 1;
        for (int i = 0; i < Math.min(leaderboardEntries.size(), 10); i++) {
            LeaderboardEntry entry = leaderboardEntries.get(i);
            String entryString = rank + ". " + entry.getPlayerName() + ": " + entry.getScore();
            entriesArray.add(entryString);
            rank++;
        }
        leaderboardList.setItems(entriesArray);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        loadLeaderboard(); // Load leaderboard data whenever the screen is shown
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parent.changeScreen(MenuState.MENU);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
