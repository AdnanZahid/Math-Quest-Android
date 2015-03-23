package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;

public class ResourceManager extends BaseGameActivity implements
		ChangeSceneListener {

	private static ResourceManager INSTANCE;

	public float HEIGHT = 480;
	public float WIDTH = 800;
	public Camera camera;

	public ITextureRegion splashTR, backgroundTR, blackboardTR;
	public TiledTextureRegion buttonTR, facebookTR, twitterTR;
	public Font buttonFont, dusterFont, scoreFont;

	public ITextureRegion blueBackgroundTR, orangeBackgroundTR,
			greenBackgroundTR, pinkBackgroundTR;
	public ITextureRegion closetsTR, glassTR;
	public ITextureRegion platformBookTR, platformBoxTR, platformRulerTR;
	public ITextureRegion plus1TR, plus10TR, plus100TR, minus1TR, minus10TR,
			minus100TR, divide10TR, divide100TR, calculatorTR, brokenRulerTR,
			pencilTR, protractorTR, tacksTR;
	public TiledTextureRegion heroTR, coinTR;
	public TiledTextureRegion shieldTR, magnetTR;
	public Font progressFont, mathDusterFont, mathButtonFont;

	public Scene scene;

	public AudioScene audioScene;
	public DifficultyScene difficultyScene;
	public GameScene gameScene;
	public GuideScene guideScene;
	public HighscoresScene highscoresScene;
	public HomeScene homeScene;
	public MathScene mathScene;
	public MenuScene menuScene;
	public SplashScene splashScene;

	public int coins = 0;
	public int score = 0;

	public Sound buttonClicked, coin, died, fadeOut, greenScore, highScore,
			magnetSound, negativeNumberSound, popSound, positiveNumberSound,
			redScoreSound, shieldSound;
	public Music homeSceneSound, gameSceneSound, mathSceneSound;

	public Engine getEngine() {
		return mEngine;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		WIDTH = displaymetrics.widthPixels;
		HEIGHT = displaymetrics.heightPixels;

		camera = new Camera(0, 0, WIDTH, HEIGHT);
		EngineOptions EO = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						WIDTH, HEIGHT), camera);
		EO.getAudioOptions().setNeedsSound(true);
		EO.getAudioOptions().setNeedsMusic(true);
		return EO;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		// homeSceneSound.pause();
		gameSceneSound.pause();
		// mathSceneSound.pause();

		super.onPause();
	}

	@Override
	protected synchronized void onResume() {
		// TODO Auto-generated method stub

		// homeSceneSound.play();
		if (gameSceneSound != null)
			gameSceneSound.play();
		// mathSceneSound.play();

		super.onResume();
	}

	public void loadSplashResources() throws IOException {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/images/");
		BuildableBitmapTextureAtlas splashTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 1024);
		splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTA, this, "splash.png");

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/images/");
		BuildableBitmapTextureAtlas backgroundTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 2048, 2048);
		backgroundTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backgroundTA, this, "background.png");

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/images/");
		BuildableBitmapTextureAtlas blackboardTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 1024);
		blackboardTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				blackboardTA, this, "blackboard.png");

		BuildableBitmapTextureAtlas buttonTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 512, 128, TextureOptions.BILINEAR);
		buttonTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				buttonTA, this, "button.png", 2, 1);

		BuildableBitmapTextureAtlas socialButtonTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		facebookTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(socialButtonTA, this, "facebook.png", 2,
						1);
		twitterTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(socialButtonTA, this, "twitter.png", 2, 1);

		BuildableBitmapTextureAtlas blueBackgroundTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		blueBackgroundTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(blueBackgroundTA, this, "blue_background.png");

		BuildableBitmapTextureAtlas orangeBackgroundTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		orangeBackgroundTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(orangeBackgroundTA, this,
						"orange_background.png");

		BuildableBitmapTextureAtlas greenBackgroundTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		greenBackgroundTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(greenBackgroundTA, this,
						"green_background.png");

		BuildableBitmapTextureAtlas pinkBackgroundTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		pinkBackgroundTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(pinkBackgroundTA, this, "pink_background.png");

		BuildableBitmapTextureAtlas closetsTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		closetsTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				closetsTA, this, "closets.png");

		BuildableBitmapTextureAtlas glassTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 512);
		glassTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				glassTA, this, "glass.png");

		BuildableBitmapTextureAtlas platformsTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 128, 128);
		platformBookTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(platformsTA, this, "book.png");
		platformBoxTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				platformsTA, this, "box.png");
		platformRulerTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(platformsTA, this, "ruler.png");

		BuildableBitmapTextureAtlas heroTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 64, TextureOptions.BILINEAR);
		heroTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				heroTA, this, "hero.png", 6, 1);
		coinTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				heroTA, this, "coins.png", 10, 1);

		BuildableBitmapTextureAtlas shieldTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 256, TextureOptions.BILINEAR);
		shieldTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				shieldTA, this, "shield.png", 10, 1);
		magnetTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				shieldTA, this, "magnet.png", 6, 1);

		BuildableBitmapTextureAtlas itemsTA = new BuildableBitmapTextureAtlas(
				getTextureManager(), 1024, 1024);
		plus1TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "plus1.png");
		plus10TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "plus10.png");
		plus100TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "plus100.png");
		minus1TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "minus1.png");
		minus10TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "minus10.png");
		minus100TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "minus100.png");
		divide10TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "divide10.png");
		divide100TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "divide100.png");
		calculatorTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "calculator.png");
		brokenRulerTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "brokenRuler.png");
		pencilTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "pencil.png");
		protractorTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "protractor.png");
		tacksTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				itemsTA, this, "tacks.png");

		buttonFont = FontFactory.createFromAsset(this.getFontManager(),
				this.getTextureManager(), 256, 256, this.getAssets(),
				"fonts/chalkboard-bold.ttf", 30, true,
				android.graphics.Color.argb(127, 0, 122, 255));

		scoreFont = FontFactory.createFromAsset(this.getFontManager(),
				this.getTextureManager(), 512, 512, this.getAssets(),
				"fonts/chalkboard-bold.ttf", 65, true,
				android.graphics.Color.WHITE);

		dusterFont = FontFactory.createFromAsset(this.getFontManager(),
				this.getTextureManager(), 512, 512, this.getAssets(),
				"fonts/chalkduster.ttf", 100, true,
				android.graphics.Color.WHITE);

		mathDusterFont = FontFactory
				.createFromAsset(this.getFontManager(),
						this.getTextureManager(), 512, 512, this.getAssets(),
						"fonts/chalkduster.ttf", 31, true,
						android.graphics.Color.WHITE);

		mathButtonFont = FontFactory.createFromAsset(this.getFontManager(),
				this.getTextureManager(), 512, 512, this.getAssets(),
				"fonts/chalkboard-bold.ttf", 60, true,
				android.graphics.Color.argb(127, 0, 122, 255));

		progressFont = FontFactory.createFromAsset(this.getFontManager(),
				this.getTextureManager(), 256, 256, this.getAssets(),
				"fonts/chalkboard-bold.ttf", 20, true,
				android.graphics.Color.argb(255, 127, 127, 127));

		SoundFactory.setAssetBasePath("sounds/");
		buttonClicked = SoundFactory.createSoundFromAsset(getSoundManager(),
				this, "Button.wav");
		coin = SoundFactory.createSoundFromAsset(getSoundManager(), this,
				"Coin.wav");
		died = SoundFactory.createSoundFromAsset(getSoundManager(), this,
				"Died.wav");
		fadeOut = SoundFactory.createSoundFromAsset(getSoundManager(), this,
				"FadeOut.wav");
		greenScore = SoundFactory.createSoundFromAsset(getSoundManager(), this,
				"GreenScore.wav");
		highScore = SoundFactory.createSoundFromAsset(getSoundManager(), this,
				"Highscore.wav");
		magnetSound = SoundFactory.createSoundFromAsset(getSoundManager(),
				this, "Magnet.wav");
		negativeNumberSound = SoundFactory.createSoundFromAsset(
				getSoundManager(), this, "NegativeNumber.wav");
		popSound = SoundFactory.createSoundFromAsset(getSoundManager(), this,
				"Pop.wav");
		positiveNumberSound = SoundFactory.createSoundFromAsset(
				getSoundManager(), this, "PositiveNumber.wav");
		redScoreSound = SoundFactory.createSoundFromAsset(getSoundManager(),
				this, "RedScore.wav");
		shieldSound = SoundFactory.createSoundFromAsset(getSoundManager(),
				this, "Shield.wav");

		MusicFactory.setAssetBasePath("sounds/");
		homeSceneSound = MusicFactory.createMusicFromAsset(getMusicManager(),
				this, "Home.wav");
		gameSceneSound = MusicFactory.createMusicFromAsset(getMusicManager(),
				this, "GameScene.wav");
		mathSceneSound = MusicFactory.createMusicFromAsset(getMusicManager(),
				this, "MathScene.wav");

		try {
			splashTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			backgroundTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			blackboardTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			buttonTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			socialButtonTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			splashTA.load();
			backgroundTA.load();
			blackboardTA.load();
			buttonTA.load();
			socialButtonTA.load();
			buttonFont.load();
			scoreFont.load();
			dusterFont.load();
			progressFont.load();
			mathDusterFont.load();
			mathButtonFont.load();

			blueBackgroundTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			orangeBackgroundTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			greenBackgroundTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			pinkBackgroundTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			closetsTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			glassTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			heroTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			platformsTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			itemsTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			shieldTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			blueBackgroundTA.load();
			orangeBackgroundTA.load();
			greenBackgroundTA.load();
			pinkBackgroundTA.load();
			closetsTA.load();
			glassTA.load();
			heroTA.load();
			platformsTA.load();
			itemsTA.load();
			shieldTA.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unloadSplashResources() {

	}

	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		// TODO Auto-generated method stub
		loadSplashResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {

		scene = new Scene();

		audioScene = new AudioScene();
		difficultyScene = new DifficultyScene();
		gameScene = new GameScene();
		guideScene = new GuideScene();
		highscoresScene = new HighscoresScene();
		homeScene = new HomeScene();
		mathScene = new MathScene();
		menuScene = new MenuScene();
		splashScene = new SplashScene();

		audioScene.setRM(this);
		difficultyScene.setRM(this);
		gameScene.setRM(this);
		guideScene.setRM(this);
		highscoresScene.setRM(this);
		homeScene.setRM(this);
		mathScene.setRM(this);
		menuScene.setRM(this);
		splashScene.setRM(this);

		audioScene.setChangeSceneListener(this);
		difficultyScene.setChangeSceneListener(this);
		gameScene.setChangeSceneListener(this);
		guideScene.setChangeSceneListener(this);
		highscoresScene.setChangeSceneListener(this);
		homeScene.setChangeSceneListener(this);
		mathScene.setChangeSceneListener(this);
		menuScene.setChangeSceneListener(this);
		splashScene.setChangeSceneListener(this);

		audioScene.initComponents();
		difficultyScene.initComponents();
		gameScene.initComponents(scene);
		guideScene.initComponents();
		highscoresScene.initComponents();
		homeScene.initComponents();
		mathScene.initComponents(scene);
		menuScene.initComponents();

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

		splashScene.populateIndividualScene(scene);
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void changeScene(String sceneName) {
		scene.detachChildren();
		if (sceneName.equals("audioScene")) {
			try {
				menuScene.unpopulateIndividualScene(scene);
				audioScene.populateIndividualScene(scene);
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("difficultyScene")) {
			try {
				menuScene.unpopulateIndividualScene(scene);
				difficultyScene.populateIndividualScene(scene);
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("gameScene")) {
			try {
				gameScene.initComponents(scene);
				homeScene.unpopulateIndividualScene(scene);
				guideScene.unpopulateIndividualScene(scene);
				mathScene.unpopulateIndividualScene(scene);
				gameScene.populateIndividualScene();
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("guideScene")) {
			try {
				homeScene.unpopulateIndividualScene(scene);
				guideScene.populateIndividualScene(scene);
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("highscoresScene")) {
			try {
				menuScene.unpopulateIndividualScene(scene);
				mathScene.unpopulateIndividualScene(scene);
				highscoresScene.populateIndividualScene(scene);
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("homeScene")) {
			try {
				gameScene.unpopulateIndividualScene(scene);
				guideScene.unpopulateIndividualScene(scene);
				menuScene.unpopulateIndividualScene(scene);
				homeScene.populateIndividualScene(scene);
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("mathScene")) {
			try {
				gameScene.unpopulateIndividualScene(scene);
				mathScene.populateIndividualScene();
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (sceneName.equals("menuScene")) {
			try {
				homeScene.unpopulateIndividualScene(scene);
				audioScene.unpopulateIndividualScene(scene);
				difficultyScene.unpopulateIndividualScene(scene);
				highscoresScene.unpopulateIndividualScene(scene);
				menuScene.populateIndividualScene(scene);
				mEngine.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}