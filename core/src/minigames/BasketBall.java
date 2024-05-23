package minigames;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.eng1.game.HeslingtonHustle;
import com.eng1.game.MenuState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasketBall implements Screen {
    private HeslingtonHustle parent;
    private SpriteBatch batch;
    private Texture basketballTexture;
    private ShapeRenderer shapeRenderer;
    private List<Sprite> basketballs;
    private List<Vector2> basketballVelocities;
    private boolean gameStarted;
    private int score;

    private Stage stage;
    private Label scoreLabel;
    private Label timerLabel;
    private Skin skin;

    private static final float HOOP_RADIUS = 50;
    private static final float HOOP_THICKNESS = 5;
    private Vector2 hoopCenter;

    private float gameTime;

    public BasketBall(HeslingtonHustle game) {
        parent = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        basketballTexture = new Texture("minigame/Basketball.png");
        basketballs = new ArrayList<>();
        basketballVelocities = new ArrayList<>();
        addNewBall(Gdx.graphics.getWidth() / 2f, 300);
        gameStarted = false;
        score = 0;

        hoopCenter = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 100);

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Initialize labels
        scoreLabel = new Label("Score: 0", skin);
        timerLabel = new Label("Time: 30", skin);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(timerLabel).left().pad(10).expandX();
        table.add(scoreLabel).right().pad(10);
        stage.addActor(table);

        gameTime = 30; // Set game timer to 30 seconds
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        showControlsDialog(); // Show the controls dialog when the screen is first displayed
    }

    private void showControlsDialog() {
        Dialog dialog = new Dialog("Controls", skin) {
            @Override
            protected void result(Object object) {
                gameStarted = true; // Start the game when "Ok" is pressed
            }
        };
        dialog.text("Press SPACE to launch the ball.");
        dialog.button("OK");
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        if (!gameStarted) {
            Gdx.gl.glClearColor(0, 0, 0, 1); // Black background when dialog is shown
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(delta);
            stage.draw();
            return; // Skip the rest of the render method if the game hasn't started
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parent.changeScreen(MenuState.PAUSE);
        }
        gameTime -= delta;
        if (gameTime <= 0) {
            gameTime = 0;
            gameStarted = false; // End the game when time runs out
            resetBasketBallGame();
            parent.changeScreen(MenuState.APPLICATION);
            return;

        }

        handleInput(delta);
        updateBallPositions(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (Sprite basketball : basketballs) {
            basketball.draw(batch);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        drawBasketballHoop();
        shapeRenderer.end();

        updateLabels();
        stage.act(delta);
        stage.draw();
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for (int i = 0; i < basketballVelocities.size(); i++) {
                Vector2 velocity = basketballVelocities.get(i);
                if (velocity.y == 0) { // If the ball is not already launched
                    float currentX = basketballs.get(i).getX();
                    float horizontalSpeed = velocity.x;
                    velocity.set(0, 400); // Launch ball upwards
                    addNewBall(currentX, horizontalSpeed); // Add a new ball at the current position with the same horizontal speed
                    break;
                }
            }
        }
    }

    private void updateBallPositions(float delta) {
        Iterator<Sprite> basketballIterator = basketballs.iterator();
        Iterator<Vector2> velocityIterator = basketballVelocities.iterator();

        while (basketballIterator.hasNext() && velocityIterator.hasNext()) {
            Sprite basketball = basketballIterator.next();
            Vector2 velocity = velocityIterator.next();

            basketball.translateX(velocity.x * delta);
            basketball.translateY(velocity.y * delta);

            if (velocity.y != 0) { // If the ball is launched
                if (checkHoopCollision(basketball)) {
                    basketballIterator.remove();
                    velocityIterator.remove();
                    score++;
                }
                if (basketball.getY() > Gdx.graphics.getHeight()) {
                    basketballIterator.remove();
                    velocityIterator.remove();
                }
            } else {
                if (basketball.getX() <= 0) {
                    basketball.setX(0); // Ensure the ball doesn't go out of bounds
                    velocity.x = Math.abs(velocity.x); // Move right
                } else if (basketball.getX() >= Gdx.graphics.getWidth() - basketball.getWidth()) {
                    basketball.setX(Gdx.graphics.getWidth() - basketball.getWidth()); // Ensure the ball doesn't go out of bounds
                    velocity.x = -Math.abs(velocity.x); // Move left
                }
            }
        }
    }

    private boolean checkHoopCollision(Sprite basketball) {
        float ballCenterX = basketball.getX() + basketball.getWidth() / 2;
        float ballCenterY = basketball.getY() + basketball.getHeight() / 2;
        float distance = hoopCenter.dst(ballCenterX, ballCenterY);

        return distance < HOOP_RADIUS;
    }

    private void addNewBall(float xPosition, float horizontalSpeed) {
        Sprite newBasketball = new Sprite(basketballTexture);
        newBasketball.setSize(50, 50);
        newBasketball.setPosition(xPosition, 50);
        basketballs.add(newBasketball);
        basketballVelocities.add(new Vector2(horizontalSpeed, 0));
    }

    private void drawBasketballHoop() {
        shapeRenderer.setColor(1, 0, 0, 1); // Set color red for Hoop
        shapeRenderer.circle(hoopCenter.x, hoopCenter.y, HOOP_RADIUS);
        shapeRenderer.setColor(0,0,0,1);
        shapeRenderer.circle(hoopCenter.x, hoopCenter.y, HOOP_RADIUS - HOOP_THICKNESS);
    }

    private void updateLabels() {
        scoreLabel.setText("Score: " + score);
        timerLabel.setText("Time: " + (int) gameTime);
    }
    private void resetBasketBallGame() {
        basketballs.clear();
        basketballVelocities.clear();
        addNewBall(Gdx.graphics.getWidth() / 2f, 300);
        score = 0;
        gameTime = 30;
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
        batch.dispose();
        basketballTexture.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        skin.dispose();
    }
}
