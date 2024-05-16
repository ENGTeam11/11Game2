package tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class AssetTests {
    @Test
    public void testCharacterAssetsExist() {
        assertTrue("The asset for player 1 exists",
                Gdx.files.internal("playerCharacters/playerCharacter1.png").exists());
        assertTrue("The asset for player 2 exists",
                Gdx.files.internal("playerCharacters/playerCharacter2.png").exists());
        assertTrue("The asset for player 3 exists",
                Gdx.files.internal("playerCharacters/playerCharacter1.png").exists());
    }
    @Test
    public void testUIskinAssetExists() {
        assertTrue("The asset for the user interface exists",
                Gdx.files.internal("skin/uiskin.png").exists());
    }

    @Test
    public void testMapAssetsExist() {
        // CS folder
        assertTrue(Gdx.files.internal("maps/cs/1_Generic_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/2_LivingRoom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/3_Bathroom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/4_Bedroom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/5_Classroom_and_library_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/18_Jail_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/20_Japanese_interiors.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/computer-science-building.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/oie_1512512TG0HYYOf.png").exists());
        assertTrue(Gdx.files.internal("maps/cs/Room_Builder_16x16.png").exists());

        // Gym folder
        assertTrue(Gdx.files.internal("maps/gym/2_LivingRoom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/gym/4_Bedroom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/gym/5_Classroom_and_library_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/gym/8_Gym_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/gym/oie_1512512TG0HYYOf.png").exists());
        assertTrue(Gdx.files.internal("maps/gym/Room_Builder_16x16.png").exists());

        //Home folder
        assertTrue(Gdx.files.internal("maps/home/1_Generic_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/2_LivingRoom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/3_Bathroom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/4_Bedroom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/5_Classroom_and_library_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/11_Halloween_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/12_Kitchen_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/13_Conference_Hall_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/20_Japanese_interiors.png").exists());
        assertTrue(Gdx.files.internal("maps/home/21_Clothing_Store.png").exists());
        assertTrue(Gdx.files.internal("maps/home/22_Museum.png").exists());
        assertTrue(Gdx.files.internal("maps/home/home.png").exists());
        assertTrue(Gdx.files.internal("maps/home/Interiors_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/home/oie_1512512TG0HYYOf.png").exists());
        assertTrue(Gdx.files.internal("maps/home/Room_Builder_16x16.png").exists());

        //map1

        assertTrue(Gdx.files.internal("maps/map1/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map1/map1.png").exists());
        assertTrue(Gdx.files.internal("maps/map1/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map1/Tileset_3_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map1/Tileset_17_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map1/Tileset_20_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map1/Tileset_40_MV.png").exists());

        //map2

        assertTrue(Gdx.files.internal("maps/map2/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/map2.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_17_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_20_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_36_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_38_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_43_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_44_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_45_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map2/Tileset_48_MV.png").exists());

        //map3

        assertTrue(Gdx.files.internal("maps/map3/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/map3.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_17_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_20_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_41_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_43_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_44_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map3/Tileset_45_MV.png").exists());

        //map4

        assertTrue(Gdx.files.internal("maps/map4/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/map4.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/Tileset_11_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/Tileset_12_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/Tileset_17_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/Tileset_79_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map4/Tileset_Buses_1_MV.png").exists());

        //map5

        assertTrue(Gdx.files.internal("maps/map5/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/map5.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/Tileset_17_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/Tileset_20_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/Tileset_52_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/Tileset_53_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map5/Tileset_54_MV.png").exists());

        //map6

        assertTrue(Gdx.files.internal("maps/map6/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/map6.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/Tileset_3_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/Tileset_17_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/Tileset_20_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/Tileset_26_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map6/Tileset_27_MV.png").exists());

        //map7

        assertTrue(Gdx.files.internal("maps/map7/A2_Floors_MV_TILESET.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/map7.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_1_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_2_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_16_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_43_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_44_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_45_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_63_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_64_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_65_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_66_MV.png").exists());
        assertTrue(Gdx.files.internal("maps/map7/Tileset_Cars_MV.png").exists());

        //piazza

        assertTrue(Gdx.files.internal("maps/piazza/1_Generic_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/2_LivingRoom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/4_Bedroom_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/5_Classroom_and_library_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/12_Kitchen_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/16_Grocery_store_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/19_Hospital_16x16.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/oie_1512512TG0HYYOf.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/piazza.png").exists());
        assertTrue(Gdx.files.internal("maps/piazza/Room_Builder_16x16.png").exists());



    }
}
