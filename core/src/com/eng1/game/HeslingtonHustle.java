package com.eng1.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import eng1.model.views.*;
import minigames.AcademicWeapon;
import minigames.BasketBall;
import minigames.FoodNinja;

/**
 * The main game class responsible for managing screens.
 */
public class HeslingtonHustle extends Game {
	private Music backgroundMusic;

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	private CharacterScreen characterScreen;
	private FoodNinja foodNinja;
	private AcademicWeapon academicWeapon;
	private PauseScreen pauseScreen;
	private LeaderboardScreen leaderboardScreen;
	private BasketBall basketBall;
	
	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
		preferences = new AppPreferences();
		Activity.setGameInstance(this); // Set the game instance in Activity

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/mixkit-kidding-around-9.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(preferences.getMusicVolume());
		if (preferences.isMusicEnabled()) {
			backgroundMusic.play();
		}
	}

	/**
	 * Retrieves the preferences instance.
	 * @return The preferences instance.
	 */
	public AppPreferences getPreferences() {
		return this.preferences;
	}

	/**
	 * Changes the current screen based on the specified screen constant.
	 * @param screen The screen constant indicating the screen to switch to.
	 *
	 */
	public void changeScreen(MenuState screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				setScreen(menuScreen);
				break;
			case PREFERENCES:
				if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if (mainScreen == null) mainScreen = new MainScreen(this);
				setScreen(mainScreen);
				break;
			case ENDGAME:
				if (endScreen == null) endScreen = new EndScreen(this);
				setScreen(endScreen);
				break;
			case CHARACTER:
				if (characterScreen == null) characterScreen = new CharacterScreen(this);
				setScreen(characterScreen);
				break;
			case PAUSE:
				if (pauseScreen == null) pauseScreen = new PauseScreen(this);
				setScreen(pauseScreen);
				break;
			case FOODNINJA:
				if(foodNinja == null) foodNinja = new FoodNinja(this);
				setScreen(foodNinja);
				break;
			case ACADEMICWEAPON:
				if(academicWeapon == null) academicWeapon = new AcademicWeapon(this);
				setScreen(academicWeapon);
				break;
			case LEADERBOARD:
				if (leaderboardScreen == null) leaderboardScreen = new LeaderboardScreen(this);
				setScreen(leaderboardScreen);
				break;
			case BASKETBALL:
				if (basketBall == null) basketBall = new BasketBall(this);
				setScreen(basketBall);
				break;
 		}
	}

	@Override
	public void render() {
		super.render();
		// Handle input events


	}
	public Music getBackgroundMusic() {
		return backgroundMusic;
	}
	public void resetgame(){
		mainScreen = new MainScreen(this);
	}
}
