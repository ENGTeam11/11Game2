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
    private Label errorLabel;
    private String selectedCharacter;

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
        setupCharacterButton(character1Button, "playerCharacters/adam.png", "Character1");
        setupCharacterButton(character2Button, "playerCharacters/amelia.png", "Character2");
        setupCharacterButton(character3Button, "playerCharacters/bob.png", "Character3");

        // Start Game button
        startGameButton = new TextButton("Start Game!", skin);
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!playerNameField.getText().isEmpty()) {
                    if (selectedCharacter != null) {
                        GameStats.setPlayerName(playerNameField.getText());
                        Play.setSelectedCharacter(selectedCharacter);
                        parent.changeScreen(MenuState.APPLICATION);
                    } else {
                        errorLabel.setText("Please select a character!");
                    }
                } else {
                    playerNameField.setMessageText("Please enter a name!");
                }
            }
        });

        // Error label
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // Red color for error message

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
        table.row().padTop(10);
        table.add(errorLabel).colspan(3).center();

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
                selectedCharacter = characterName;
                highlightSelectedButton(button);
                errorLabel.setText(""); // Clear error message if any
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
