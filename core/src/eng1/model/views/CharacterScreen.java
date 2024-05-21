package eng1.model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.eng1.game.*;

public class CharacterScreen extends ScreenAdapter {
    private HeslingtonHustle parent;
    private Stage stage;
    private Skin skin;
    private TextButton character1Button, character2Button, character3Button;
    private TextButton startGameButton;
    private Label titleLabel;
    private TextField playerNameField;

    public CharacterScreen(HeslingtonHustle game) {
        parent = game;
        stage = new Stage(new StretchViewport(800, 600));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        // Setup UI
        setupUi();
    }

    private void setupUi() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Create title and player name input
        titleLabel = new Label("Character Selection", skin);
        playerNameField = new TextField("", skin);
        playerNameField.setMessageText("Enter your player name");

        // Initialize character buttons
        character1Button = new TextButton("", skin);
        character2Button = new TextButton("", skin);
        character3Button = new TextButton("", skin);

        // Setup character images
        setupCharacterButton(character1Button, "playerCharacters/playerCharacter1.png", "Character1");
        setupCharacterButton(character2Button, "playerCharacters/playerCharacter2.png", "Character2");
        setupCharacterButton(character3Button, "playerCharacters/playerCharacter3.png", "Character3");

        // Start Game button
        startGameButton = new TextButton("Start Game!", skin);
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!playerNameField.getText().isEmpty()) {
                    GameStats.setPlayerName(playerNameField.getText());
                    parent.changeScreen(MenuState.APPLICATION);
                } else {
                    playerNameField.setMessageText("Please enter a name!");
                }
            }
        });

        // Layout
        table.add(titleLabel).colspan(3).padBottom(20);
        table.row();
        table.add(playerNameField).colspan(3).fillX().padBottom(20);
        table.row();
        table.add(character1Button).padRight(10);
        table.add(character2Button).padRight(10);
        table.add(character3Button);
        table.row().padTop(20);
        table.add(startGameButton).colspan(3).fillX();

        // Set the input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void setupCharacterButton(TextButton button, String imagePath, final String characterName) {
        Texture characterTexture = new Texture(Gdx.files.internal(imagePath));
        Image characterImage = new Image(new TextureRegionDrawable(new TextureRegion(characterTexture)));

        button.add(characterImage).size(100, 100);
        button.row();
        button.add(new Label(characterName, skin));

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Play.setSelectedCharacter(characterName);
                highlightSelectedButton(button);
            }
        });
    }

    private void highlightSelectedButton(TextButton selectedButton) {
        // Reset style for all buttons
        character1Button.setStyle(skin.get(TextButton.TextButtonStyle.class));
        character2Button.setStyle(skin.get(TextButton.TextButtonStyle.class));
        character3Button.setStyle(skin.get(TextButton.TextButtonStyle.class));

        // Highlight the selected button
        TextButton.TextButtonStyle highlightedStyle = new TextButton.TextButtonStyle(selectedButton.getStyle());
        highlightedStyle.fontColor = com.badlogic.gdx.graphics.Color.YELLOW;
        selectedButton.setStyle(highlightedStyle);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parent.changeScreen(MenuState.MENU);
        }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
