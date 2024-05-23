package tests;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.eng1.game.MapManager;
import com.eng1.game.Player;
import com.eng1.game.PlayerTracker;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.ApplicationAdapter;

@RunWith(GdxTestRunner.class)
public class MovementTests {
    private static class TestGame extends ApplicationAdapter {
        Player player;
        MapManager mapManager;
        PlayerTracker playerTracker;
        AssetManager assetManager;
        OrthographicCamera orthographicCamera;

        @Override
        public void create() {
            // Mock objects for testing
            Texture texture = new Texture("playerCharacters/playerCharacter1.png");
            Sprite sprite = new Sprite(texture);
            mapManager = new FakeMapManager(assetManager, orthographicCamera);
            playerTracker = new PlayerTracker(mapManager);
            player = new Player(sprite, playerTracker, mapManager);
            playerTracker.setPlayer(player);

        }

        public void simulateKeyPress(int keycode) {
            player.keyDown(keycode);
            player.update(1.0f / 60.0f, mapManager);
            player.keyUp(keycode);
        }
    }

    @Test
    public void testPlayerMovement() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        TestGame testGame = new TestGame();
        HeadlessApplication app = new HeadlessApplication(testGame, config);

        // Simulate pressing the WASD keys
        testGame.simulateKeyPress(com.badlogic.gdx.Input.Keys.W); // Move up
        assertEquals(new Vector2(0, 300.0f / 60.0f), new Vector2(testGame.player.getX(), testGame.player.getY()));

        testGame.simulateKeyPress(com.badlogic.gdx.Input.Keys.S); // Move down
        assertEquals(new Vector2(0, 0), new Vector2(testGame.player.getX(), testGame.player.getY()));

        testGame.simulateKeyPress(com.badlogic.gdx.Input.Keys.A); // Move left
        assertEquals(new Vector2(-300.0f / 60.0f, 0), new Vector2(testGame.player.getX(), testGame.player.getY()));

        testGame.simulateKeyPress(com.badlogic.gdx.Input.Keys.D); // Move right
        assertEquals(new Vector2(0, 0), new Vector2(testGame.player.getX(), testGame.player.getY()));

        app.exit();
    }
}


// Fake classes for MapManager and PlayerTracker
class FakeMapManager extends MapManager {
    public FakeMapManager(AssetManager assetManager, OrthographicCamera camera) {
        super(assetManager, camera);
    }

    @Override
    public float getCurrentScale() {
        return 1.0f;
        //Setting this to 1 makes the calculations easier and the scale will not affect the outcome of the test
    }

    @Override
    public Vector2 findObjectPosition(String layer, String name) {
        return new Vector2(0, 0);
        //This method will only be used to fetch the spawn points for the test player, therefore we simply return (0,0)
    }

    @Override
    public boolean inRegion(Vector2 position, float width, float height, String region) {
        return false; // No collision detection is necessary here
    }
}

class FakePlayerTracker extends PlayerTracker {
    public FakePlayerTracker(MapManager mapManager) {
        super(mapManager);
    }

    @Override
    public void checkPlayerTile(float x, float y) {
        //Do not need to do anything here as we are only testing the movement
    }


    @Override
    public String checkPlayerActivity(Vector2 position, float width, float height) {
        //Do not need to return anything here as we are only testing the movement
        return null;
    }
}