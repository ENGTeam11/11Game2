package tests;

import com.badlogic.gdx.Gdx;
import eng1.model.views.CharacterScreen;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class AssetTests {
    @Test
    public void testShipAssetExists() {
        assertTrue("The asset for player 1 exists",
                Gdx.files.internal("playerCharacters/playerCharacter1.png").exists());
    }
}
