package org.eu5.adnan_zahid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameScene implements OnClickListener, IOnSceneTouchListener,
		IAccelerationListener, IUpdateHandler, ContactListener {

	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	private Scene scene;

	private PhysicsWorld mPhysicsWorld;

	private static final FixtureDef FIXTURE_DEF = PhysicsFactory
			.createFixtureDef(1, 0, 0);

	private int startingX = 50;
	private int startingY = 50;

	private int score;
	private int coins;
	private int feet;

	private boolean isShowScore;
	private boolean isShield;
	private boolean isMagnet;

	private Text progressLabel;

	private AnimatedSprite heroSprite, shieldSprite, magnetSprite;
	private Body heroBody, roofBody, groundBody, leftBody;

	private int playerVelocity;
	private int distanceMoved;
	private float positionX;

	private Random random = new Random();

	private int minPlatformLength = 7;
	private int maxPlatformLength = 13;

	private int initialRandom = 0;
	private int maxRandom = 0;

	private ITextureRegion backgroundTRArray[];
	private ITextureRegion groundTRArray[];
	private ITextureRegion positiveNumberTRArray[];
	private ITextureRegion negativeNumberTRArray[];

	private AutoParallaxBackground background;
	private ParallaxEntity backgroundSpriteParallax, closetsSpriteParallax,
			glassSpriteParallax, progressSpriteParallax;
	private Sprite backgroundSprite, closetsSprite, glassSprite;

	private boolean gameOver;
	private boolean addObstacles;
	private boolean touchLeft;

	private float scaleX = 0;
	private float scaleY = 0;

	private float jumpHeight = 0;
	private float gravity = 0;

	private int timeSinceRuler;
	private int timeSinceShield;

	private ArrayList<Sprite> coinSpriteArray;

	public void initComponents(Scene scene) {

		score = 0;
		coins = 0;
		feet = 0;

		isShowScore = false;
		isShield = false;
		isMagnet = false;

		playerVelocity = 3;
		distanceMoved = 0;
		positionX = 0;

		gameOver = false;
		addObstacles = false;
		touchLeft = false;

		timeSinceRuler = 0;
		timeSinceShield = 0;

		coinSpriteArray = new ArrayList<Sprite>();

		this.scene = scene;

		gravity = SensorManager.GRAVITY_JUPITER * scaleY;
		jumpHeight = gravity * 2 * scaleY;

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, -gravity), false);

		scene.setOnSceneTouchListener(this);

		mPhysicsWorld.setContactListener(this);

		backgroundTRArray = new ITextureRegion[] { RM.blueBackgroundTR,
				RM.orangeBackgroundTR, RM.greenBackgroundTR,
				RM.pinkBackgroundTR };

		int backgroundNumber = random.nextInt(4);
		backgroundSprite = new Sprite(RM.WIDTH / 2, RM.HEIGHT / 2,
				backgroundTRArray[backgroundNumber],
				RM.getVertexBufferObjectManager());
		closetsSprite = new Sprite(RM.WIDTH / 2, RM.HEIGHT / 2, RM.closetsTR,
				RM.getVertexBufferObjectManager());
		glassSprite = new Sprite(RM.WIDTH / 2, RM.HEIGHT / 2, RM.glassTR,
				RM.getVertexBufferObjectManager());

		maxRandom = (int) RM.HEIGHT / 2;

		scaleX = RM.WIDTH / backgroundSprite.getWidth();
		scaleY = RM.HEIGHT / backgroundSprite.getHeight();
		backgroundSprite.setScale(scaleX, scaleY);
		closetsSprite.setScale(scaleX, scaleY);
		glassSprite.setScale(scaleX, scaleY);

		progressLabel = new Text(RM.WIDTH * 0.79f, RM.HEIGHT * 0.93f,
				RM.progressFont, "      Coins: " + coins + "      Distance: "
						+ feet + " feet",
				new TextOptions(HorizontalAlign.RIGHT),
				RM.getVertexBufferObjectManager());

		progressLabel.setScale(scaleX, scaleY);

		background = new AutoParallaxBackground(0, 0, 0, 1);

		initialRandom = random.nextInt(maxRandom);

		heroSprite = new AnimatedSprite(startingX, startingY + initialRandom
				+ 28, RM.heroTR, RM.getVertexBufferObjectManager());
		heroSprite.setScale(scaleX, scaleY);
		int firstTileIndex = 0;
		int lastTileIndex = 3;
		long frameDuration[] = { 100, 100, 100, 100 };
		startAnimation(heroSprite, frameDuration, firstTileIndex, lastTileIndex);

		magnetSprite = new AnimatedSprite(startingX, startingY + initialRandom
				+ 28, RM.magnetTR, RM.getVertexBufferObjectManager());
		magnetSprite.setScale(scaleX, scaleY);
		firstTileIndex = 0;
		lastTileIndex = 5;
		long magnetDuration[] = { 100, 100, 100, 100, 100, 100 };
		startAnimation(magnetSprite, magnetDuration, firstTileIndex,
				lastTileIndex);
		magnetSprite.setVisible(isMagnet);

		heroBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, heroSprite,
				BodyType.DynamicBody, FIXTURE_DEF);
		heroBody.setUserData(new UserData("HERO", heroSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				heroSprite, heroBody, true, false));

		shieldSprite = new AnimatedSprite(startingX, startingY + initialRandom
				+ 28, RM.shieldTR, RM.getVertexBufferObjectManager());
		shieldSprite.setScale(scaleX, scaleY);
		firstTileIndex = 0;
		lastTileIndex = 9;
		long shieldDuration[] = { 100, 100, 100, 100, 100, 100, 100, 100, 100,
				100 };
		startAnimation(shieldSprite, shieldDuration, firstTileIndex,
				lastTileIndex);
		shieldSprite.setVisible(isShield);

		groundTRArray = new ITextureRegion[] { RM.platformBookTR,
				RM.platformBoxTR, RM.platformRulerTR };
		positiveNumberTRArray = new ITextureRegion[] { RM.plus1TR, RM.plus10TR,
				RM.plus100TR };
		negativeNumberTRArray = new ITextureRegion[] { RM.minus1TR,
				RM.minus10TR, RM.minus100TR, RM.divide10TR, RM.divide100TR,
				RM.calculatorTR };

		FixtureDef TACKS_DEF = PhysicsFactory.createFixtureDef(1, 0.75f, 100);

		Sprite roofSprite = new Sprite(0, 0, RM.tacksTR,
				RM.getVertexBufferObjectManager());
		roofSprite.setScale(scaleX, scaleY);
		roofSprite.setPosition(roofSprite.getWidth() / 2, RM.HEIGHT
				- roofSprite.getHeight() / 2);

		roofBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, roofSprite,
				BodyType.KinematicBody, FIXTURE_DEF);
		roofBody.setUserData(new UserData("ROOF", roofSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				roofSprite, roofBody, true, false));

		Rectangle leftSprite = new Rectangle(RM.WIDTH / 2, RM.HEIGHT / 2, 50,
				RM.HEIGHT, RM.getVertexBufferObjectManager());
		// scene.attachChild(leftSprite);

		leftBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, leftSprite,
				BodyType.KinematicBody, FIXTURE_DEF);
		leftBody.setUserData(new UserData("LEFT", null));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				leftSprite, leftBody, true, false));

		Sprite groundSprite = new Sprite(0, 0, RM.tacksTR,
				RM.getVertexBufferObjectManager());
		groundSprite.setScale(scaleX, scaleY);
		groundSprite.setPosition(groundSprite.getWidth() / 2,
				groundSprite.getHeight() / 2);

		groundBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld,
				groundSprite, BodyType.KinematicBody, TACKS_DEF);
		groundBody.setUserData(new UserData("GROUND", groundSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				groundSprite, groundBody, true, false));
	}

	public void populateIndividualScene() throws IOException {

		scene.registerUpdateHandler(this.mPhysicsWorld);
		scene.registerUpdateHandler(this);

		backgroundSpriteParallax = new ParallaxEntity(0f, backgroundSprite);
		closetsSpriteParallax = new ParallaxEntity(-15f, closetsSprite);
		glassSpriteParallax = new ParallaxEntity(-30f, glassSprite);
		progressSpriteParallax = new ParallaxEntity(0f, progressLabel);

		background.attachParallaxEntity(backgroundSpriteParallax);
		background.attachParallaxEntity(closetsSpriteParallax);
		background.attachParallaxEntity(glassSpriteParallax);
		background.attachParallaxEntity(progressSpriteParallax);

		scene.setBackground(background);
		scene.setBackgroundEnabled(true);

		scene.attachChild(magnetSprite);
		scene.attachChild(heroSprite);
		scene.attachChild(shieldSprite);

		positionX = addPlatform(groundTRArray, startingX, startingY
				+ initialRandom, 1);

		addCoins();

		positionX = addPlatform(groundTRArray, positionX,
				startingY + random.nextInt(maxRandom), 1);

		addCoins();

		positionX = addPlatform(groundTRArray, positionX,
				startingY + random.nextInt(maxRandom), 1);

		addCoins();

		positionX = addPlatform(groundTRArray, positionX,
				startingY + random.nextInt(maxRandom), 1);

		addCoins();

		// RM.gameSceneSound.play();
		// RM.homeSceneSound.pause();
		// RM.mathSceneSound.pause();
	}

	public void unpopulateIndividualScene(Scene scene) {

		scene.setBackgroundEnabled(false);

		background.clearBackgroundModifiers();

		background.detachParallaxEntity(backgroundSpriteParallax);
		background.detachParallaxEntity(closetsSpriteParallax);
		background.detachParallaxEntity(glassSpriteParallax);
		background.detachParallaxEntity(progressSpriteParallax);

		scene.detachChildren();

		heroBody.setActive(false);
		mPhysicsWorld.destroyBody(heroBody);

		roofBody.setActive(false);
		mPhysicsWorld.destroyBody(roofBody);

		groundBody.setActive(false);
		mPhysicsWorld.destroyBody(groundBody);

		RM.camera.setCenter(RM.WIDTH / 2, RM.HEIGHT / 2);

		scene.unregisterUpdateHandler(this);
	}

	private float addPlatform(ITextureRegion[] groundTRArray, float x, float y,
			int nonBook) {

		float X = x;

		int numberOfBlocks = random.nextInt(maxPlatformLength
				- minPlatformLength)
				+ minPlatformLength;
		int whichTR = nonBook + random.nextInt(groundTRArray.length - nonBook);

		int obstaclePositionX = random.nextInt(numberOfBlocks);

		for (int i = 0; i < numberOfBlocks; i++) {

			Sprite platformSprite = new Sprite(X, y, groundTRArray[whichTR],
					RM.getVertexBufferObjectManager());
			platformSprite.setScale(scaleX, scaleY);
			X += platformSprite.getWidth() * scaleX;

			Body platformBody = PhysicsFactory.createBoxBody(
					this.mPhysicsWorld, platformSprite, BodyType.StaticBody,
					FIXTURE_DEF);
			if (whichTR == 0)
				platformBody.setUserData(new UserData("BOOK", platformSprite));
			else {
				platformBody
						.setUserData(new UserData("NONBOOK", platformSprite));

				if (i == obstaclePositionX
						&& obstaclePositionX != numberOfBlocks
						&& obstaclePositionX != numberOfBlocks - 1
						&& obstaclePositionX != numberOfBlocks - 2
						&& obstaclePositionX != numberOfBlocks - 3
						&& obstaclePositionX != numberOfBlocks - 4
						&& addObstacles == true) {
					if (obstaclePositionX % 2 == 0) {

						Sprite protractorSprite = new Sprite(0, 0,
								RM.protractorTR,
								RM.getVertexBufferObjectManager());
						protractorSprite.setScale(scaleX, scaleY);
						protractorSprite.setPosition(X,
								y + protractorSprite.getHeight() * scaleY
										* 0.825f);

						Body protractorBody = PhysicsFactory.createBoxBody(
								this.mPhysicsWorld, protractorSprite,
								BodyType.KinematicBody, FIXTURE_DEF);
						protractorBody.setUserData(new UserData("OBSTACLE",
								protractorSprite));

						startAnimationBackForth(protractorSprite, 0.5f,
								protractorSprite.getWidth() * 0.75f);

						scene.attachChild(protractorSprite);
						this.mPhysicsWorld
								.registerPhysicsConnector(new PhysicsConnector(
										protractorSprite, protractorBody, true,
										false));
					} else {
						Sprite pencilSprite = new Sprite(0, 0, RM.pencilTR,
								RM.getVertexBufferObjectManager());
						pencilSprite.setScale(scaleX, scaleY);
						pencilSprite.setPosition(X,
								y + pencilSprite.getHeight() * 0.75f);

						Body pencilBody = PhysicsFactory.createBoxBody(
								this.mPhysicsWorld, pencilSprite,
								BodyType.KinematicBody, FIXTURE_DEF);
						pencilBody.setUserData(new UserData("OBSTACLE",
								pencilSprite));

						startAnimationUpDown(pencilSprite, 0.25f,
								pencilSprite.getHeight() * scaleY * 0.15f);

						scene.attachChild(pencilSprite);
						this.mPhysicsWorld
								.registerPhysicsConnector(new PhysicsConnector(
										pencilSprite, pencilBody, true, false));
					}
				}
			}
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
					platformSprite, platformBody));

			scene.attachChild(platformSprite);
		}

		return X + heroSprite.getWidth() * 4 * scaleX;
	}

	private void startAnimationBackForth(Sprite sprite, float duration,
			float displacement) {
		float from = sprite.getX();
		float plusTo = from + displacement;
		float minusTo = from - displacement;

		MoveXModifier rightMovement1 = new MoveXModifier(duration, from, plusTo);
		MoveXModifier leftMovement1 = new MoveXModifier(duration, plusTo, from);
		MoveXModifier leftMovement2 = new MoveXModifier(duration, from, minusTo);
		MoveXModifier rightMovement2 = new MoveXModifier(duration, minusTo,
				from);

		SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(
				rightMovement1, leftMovement1, leftMovement2, rightMovement2) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.reset();
			}
		};
		sequenceEntityModifier.setAutoUnregisterWhenFinished(false);
		sprite.registerEntityModifier(sequenceEntityModifier);
	}

	private void startAnimationUpDown(Sprite sprite, float duration,
			float displacement) {
		float from = sprite.getY();
		float plusTo = from + displacement;
		float minusTo = from - displacement;

		MoveYModifier upMovement1 = new MoveYModifier(duration, from, plusTo);
		MoveYModifier downMovement1 = new MoveYModifier(duration, plusTo, from);
		MoveYModifier downMovement2 = new MoveYModifier(duration, from, minusTo);
		MoveYModifier upMovement2 = new MoveYModifier(duration, minusTo, from);

		SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(
				upMovement1, downMovement1, downMovement2, upMovement2) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.reset();
			}
		};
		sequenceEntityModifier.setAutoUnregisterWhenFinished(false);
		sprite.registerEntityModifier(sequenceEntityModifier);
	}

	void startAnimation(AnimatedSprite sprite, long frameDuration[],
			int firstTileIndex, int lastTileIndex) {

		sprite.animate(frameDuration, firstTileIndex, lastTileIndex, true,
				new IAnimationListener() {

					@Override
					public void onAnimationStarted(
							AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {

					}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {

					}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite pAnimatedSprite, int pOldFrameIndex,
							int pNewFrameIndex) {

					}

					@Override
					public void onAnimationFinished(
							AnimatedSprite pAnimatedSprite) {

					}
				});
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub

		if (this.mPhysicsWorld != null) {
			if (pSceneTouchEvent.isActionDown()) {

				touchLeft = true;
			}
		}

		return true;
	}

	private void addShield() {

		AnimatedSprite shieldSprite = new AnimatedSprite(positionX, startingY
				+ random.nextInt(maxRandom), RM.shieldTR,
				RM.getVertexBufferObjectManager());
		shieldSprite.setScale(scaleX, scaleY);
		long frameDuration[] = { 100, 100, 100, 100, 100, 100, 100, 100, 100,
				100 };

		int firstTileIndex = 0;
		int lastTileIndex = RM.shieldTR.getTileCount() - 1;

		shieldSprite.animate(frameDuration, firstTileIndex, lastTileIndex,
				true, new IAnimationListener() {

					@Override
					public void onAnimationStarted(
							AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {

					}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {

					}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite pAnimatedSprite, int pOldFrameIndex,
							int pNewFrameIndex) {

					}

					@Override
					public void onAnimationFinished(
							AnimatedSprite pAnimatedSprite) {

					}
				});
		scene.attachChild(shieldSprite);

		Body shieldBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld,
				shieldSprite, BodyType.StaticBody, FIXTURE_DEF);
		shieldBody.setUserData(new UserData("SHIELD", shieldSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				shieldSprite, shieldBody));
	}

	private void addMagnet() {

		AnimatedSprite magnetSprite = new AnimatedSprite(positionX, startingY
				+ random.nextInt(maxRandom), RM.magnetTR,
				RM.getVertexBufferObjectManager());
		magnetSprite.setScale(scaleX, scaleY);
		long frameDuration[] = { 100, 100, 100, 100, 100, 100 };

		int firstTileIndex = 0;
		int lastTileIndex = RM.magnetTR.getTileCount() - 1;

		magnetSprite.animate(frameDuration, firstTileIndex, lastTileIndex,
				true, new IAnimationListener() {

					@Override
					public void onAnimationStarted(
							AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {

					}

					@Override
					public void onAnimationLoopFinished(
							AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {

					}

					@Override
					public void onAnimationFrameChanged(
							AnimatedSprite pAnimatedSprite, int pOldFrameIndex,
							int pNewFrameIndex) {

					}

					@Override
					public void onAnimationFinished(
							AnimatedSprite pAnimatedSprite) {

					}
				});
		scene.attachChild(magnetSprite);

		Body magnetBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld,
				magnetSprite, BodyType.StaticBody, FIXTURE_DEF);
		magnetBody.setUserData(new UserData("MAGNET", magnetSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				magnetSprite, magnetBody));
	}

	private void addCoins() {

		for (int i = 0; i < 4; i++) {

			AnimatedSprite coinSprite = new AnimatedSprite(positionX + i * 50,
					startingY + random.nextInt(maxRandom), RM.coinTR,
					RM.getVertexBufferObjectManager());
			coinSprite.setScale(scaleX, scaleY);
			long frameDuration[] = { 100, 100, 100, 100, 100, 100, 100, 100,
					100, 100 };

			int firstTileIndex = 0;
			int lastTileIndex = RM.coinTR.getTileCount() - 1;

			coinSprite.animate(frameDuration, firstTileIndex, lastTileIndex,
					true, new IAnimationListener() {

						@Override
						public void onAnimationStarted(
								AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {

						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite pAnimatedSprite,
								int pRemainingLoopCount, int pInitialLoopCount) {

						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite pAnimatedSprite,
								int pOldFrameIndex, int pNewFrameIndex) {

						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite pAnimatedSprite) {

						}
					});
			scene.attachChild(coinSprite);

			Body coinBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld,
					coinSprite, BodyType.StaticBody, FIXTURE_DEF);
			coinBody.setUserData(new UserData("COIN", coinSprite));
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
					coinSprite, coinBody));

			coinSprite.setUserData(new BodyData(coinBody));
			coinSpriteArray.add(coinSprite);
		}
	}

	private void addPositiveNumber() {

		int whichTR = random.nextInt(positiveNumberTRArray.length);
		Sprite positiveNumberSprite = new Sprite(positionX, initialRandom,
				positiveNumberTRArray[whichTR],
				RM.getVertexBufferObjectManager());
		positiveNumberSprite.setScale(scaleX, scaleY);
		scene.attachChild(positiveNumberSprite);

		String numberString = "";

		if (whichTR == 0) {
			numberString = "PLUS_1";
		} else if (whichTR == 1) {
			numberString = "PLUS_10";
		} else if (whichTR == 2) {
			numberString = "PLUS_100";
		}

		Body positiveNumberBody = PhysicsFactory.createBoxBody(
				this.mPhysicsWorld, positiveNumberSprite, BodyType.StaticBody,
				FIXTURE_DEF);
		positiveNumberBody.setUserData(new UserData(numberString,
				positiveNumberSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				positiveNumberSprite, positiveNumberBody));
	}

	private void addNegativeNumber() {

		int whichTR = random.nextInt(negativeNumberTRArray.length);
		Sprite negativeNumberSprite = new Sprite(positionX,
				initialRandom + 100, negativeNumberTRArray[whichTR],
				RM.getVertexBufferObjectManager());
		negativeNumberSprite.setScale(scaleX, scaleY);
		scene.attachChild(negativeNumberSprite);

		String numberString = "";

		if (whichTR == 0) {
			numberString = "MINUS_1";
		} else if (whichTR == 1) {
			numberString = "MINUS_10";
		} else if (whichTR == 2) {
			numberString = "MINUS_100";
		} else if (whichTR == 3) {
			numberString = "DIVIDE_10";
		} else if (whichTR == 4) {
			numberString = "DIVIDE_100";
		} else if (whichTR == 5) {
			numberString = "CALCULATOR";
		}

		Body negativeNumberBody = PhysicsFactory.createBoxBody(
				this.mPhysicsWorld, negativeNumberSprite, BodyType.StaticBody,
				FIXTURE_DEF);
		negativeNumberBody.setUserData(new UserData(numberString,
				negativeNumberSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				negativeNumberSprite, negativeNumberBody));
	}

	private void addRuler() {

		Sprite brokenRulerSprite = new Sprite(positionX, startingY
				+ random.nextInt(maxRandom), RM.brokenRulerTR,
				RM.getVertexBufferObjectManager());
		brokenRulerSprite.setScale(scaleX, scaleY);
		scene.attachChild(brokenRulerSprite);

		Body brokenRulerBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld,
				brokenRulerSprite, BodyType.KinematicBody, FIXTURE_DEF);
		brokenRulerBody.setUserData(new UserData("BROKEN_RULER",
				brokenRulerSprite));
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				brokenRulerSprite, brokenRulerBody));

		brokenRulerBody.setLinearVelocity(-playerVelocity * 4, 0);
		brokenRulerBody.setAngularVelocity(3.1415f);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub

		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();

		if (((UserData) bodyA.getUserData()).getString().equals("HERO")
				|| ((UserData) bodyB.getUserData()).getString().equals("HERO")) {
			if (((UserData) bodyA.getUserData()).getString().equals("NONBOOK")
					|| ((UserData) bodyB.getUserData()).getString().equals(
							"NONBOOK")) {

				int firstTileIndex = 4;
				int lastTileIndex = 5;
				long frameDuration[] = { 100, 100 };
				startAnimation(heroSprite, frameDuration, firstTileIndex,
						lastTileIndex);
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"BOOK")) {
				bodyA.setType(BodyType.DynamicBody);
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"BOOK")) {
				bodyB.setType(BodyType.DynamicBody);
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"ROOF")) {

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"ROOF")) {

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"GROUND")) {

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;
					jump();

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"GROUND")) {

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;
					jump();

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"COIN")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				coins++;
				RM.coin.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"COIN")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				coins++;
				RM.coin.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"SHIELD")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				isShield = true;
				isMagnet = false;
				RM.shieldSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"SHIELD")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				isShield = true;
				isMagnet = false;
				RM.shieldSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"MAGNET")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				isMagnet = true;
				isShield = false;
				RM.magnetSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"MAGNET")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				isMagnet = true;
				isShield = false;
				RM.magnetSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"PLUS_1")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				score++;
				isShowScore = false;
				RM.positiveNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"PLUS_1")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				score++;
				isShowScore = false;
				RM.positiveNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"PLUS_10")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				score += 10;
				isShowScore = false;
				RM.positiveNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"PLUS_10")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				score += 10;
				isShowScore = false;
				RM.positiveNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"PLUS_100")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				score += 100;
				isShowScore = false;
				RM.positiveNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"PLUS_100")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				score += 100;
				isShowScore = false;
				RM.positiveNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"MINUS_1")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				if (score > 0)
					score--;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"MINUS_1")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				if (score > 0)
					score--;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"MINUS_10")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				if (score > 9)
					score -= 10;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"MINUS_10")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				if (score > 9)
					score -= 10;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"MINUS_100")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				if (score > 99)
					score -= 100;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"MINUS_100")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				if (score > 99)
					score -= 100;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"DIVIDE_10")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				score /= 10;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"DIVIDE_10")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				score /= 10;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"DIVIDE_100")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				score /= 100;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"DIVIDE_100")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				score /= 100;
				isShowScore = false;
				RM.negativeNumberSound.play();
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"CALCULATOR")) {
				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				isShowScore = true;
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"CALCULATOR")) {
				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				isShowScore = true;
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"BROKEN_RULER")) {

				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;
					jump();

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"BROKEN_RULER")) {

				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;
					jump();

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyA.getUserData()).getString().equals(
					"OBSTACLE")) {

				bodyA.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyA);
				scene.detachChild(sprite);

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;
					jump();

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			} else if (((UserData) bodyB.getUserData()).getString().equals(
					"OBSTACLE")) {

				bodyB.setActive(false);
				Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
						.getSprite();
				mPhysicsWorld.destroyBody(bodyB);
				scene.detachChild(sprite);

				if (!isShield && !isMagnet) {
					gameOver = true;
				} else {
					isShield = false;
					isMagnet = false;
					jump();

					for (Sprite singleCoin : coinSpriteArray) {
						singleCoin.clearEntityModifiers();
					}
				}
			}
		}
		if (((UserData) bodyA.getUserData()).getString().equals("LEFT")) {
			bodyA.setActive(false);
			Sprite sprite = (Sprite) ((UserData) bodyA.getUserData())
					.getSprite();
			mPhysicsWorld.destroyBody(bodyA);
			scene.detachChild(sprite);

			isShowScore = true;
		} else if (((UserData) bodyB.getUserData()).getString().equals("LEFT")) {
			bodyB.setActive(false);
			Sprite sprite = (Sprite) ((UserData) bodyB.getUserData())
					.getSprite();
			mPhysicsWorld.destroyBody(bodyB);
			scene.detachChild(sprite);

			isShowScore = true;
		}
	}

	private void jump() {

		RM.popSound.play();
		heroBody.applyLinearImpulse(0, jumpHeight / 2,
				heroBody.getWorldCenter().x, heroBody.getWorldCenter().y);
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub

		if (!gameOver) {

			if (touchLeft) {
				touchLeft = false;
				int firstTileIndex = 0;
				int lastTileIndex = 3;
				long frameDuration[] = { 100, 100, 100, 100 };
				startAnimation(heroSprite, frameDuration, firstTileIndex,
						lastTileIndex);

				heroBody.applyLinearImpulse(0, jumpHeight,
						heroBody.getWorldCenter().x,
						heroBody.getWorldCenter().y);
			}

			if (isMagnet) {

				for (Sprite singleCoin : coinSpriteArray) {
					MoveYModifier moveTowardsPlayer = new MoveYModifier(0.1f,
							singleCoin.getY(), heroSprite.getY());
					singleCoin.registerEntityModifier(moveTowardsPlayer);
				}

				ArrayList<Sprite> coinSpriteRemoveArray = new ArrayList<Sprite>();

				for (Sprite singleCoin : coinSpriteArray) {
					MoveXModifier moveTowardsPlayer = new MoveXModifier(10,
							singleCoin.getX(), heroSprite.getX());
					singleCoin.registerEntityModifier(moveTowardsPlayer);

					if (singleCoin.collidesWith(heroSprite)) {

						coinSpriteRemoveArray.add(singleCoin);

						RM.coin.play();

						Body coinBody = (Body) ((BodyData) singleCoin
								.getUserData()).getBody();
						coinBody.setActive(false);
						mPhysicsWorld.destroyBody(coinBody);
						scene.detachChild(singleCoin);

						coins++;
					}
				}

				for (Sprite removeSingleCoin : coinSpriteRemoveArray) {

					coinSpriteArray.remove(removeSingleCoin);
				}
			}

			heroBody.setLinearVelocity(playerVelocity,
					heroBody.getLinearVelocity().y);
			roofBody.setLinearVelocity(playerVelocity, 0);
			groundBody.setLinearVelocity(playerVelocity, 0);
			leftBody.setLinearVelocity(playerVelocity, 0);

			if (isShowScore) {
				progressLabel.setText("      Score: " + score + "      Coins: "
						+ coins + "      Distance: " + feet + " feet");
				progressLabel.setPosition(RM.WIDTH * 0.66f, RM.HEIGHT * 0.93f);
			} else {
				progressLabel.setText("      Coins: " + coins
						+ "      Distance: " + feet + " feet");
				progressLabel.setPosition(RM.WIDTH * 0.75f, RM.HEIGHT * 0.93f);
			}

			distanceMoved += playerVelocity;

			shieldSprite.setVisible(isShield);
			magnetSprite.setVisible(isMagnet);

			shieldSprite.setPosition(heroSprite);
			magnetSprite.setPosition(heroSprite);

			RM.camera.setCenter(heroSprite.getX() + RM.WIDTH / 2 - 50,
					RM.HEIGHT / 2);

			if (distanceMoved > RM.WIDTH / 2) {

				distanceMoved = 0;
				feet++;
				timeSinceRuler++;
				timeSinceShield++;

				positionX = addPlatform(groundTRArray, positionX, startingY
						+ random.nextInt(maxRandom), 0);

				addCoins();
				addObstacles = true;

				if (score >= 100) {
					int randomBoolean = random.nextInt() % 2;
					if (randomBoolean == 0) {
						addNegativeNumber();
					} else if (randomBoolean == 1) {
						addPositiveNumber();
					}
				} else {
					addPositiveNumber();
				}

				if (timeSinceShield >= 5) {
					timeSinceShield = 0;
					int randomBoolean = random.nextInt() % 2;
					if (!isShield) {
						if (randomBoolean == 0) {
							addShield();
						}
					}
					if (!isMagnet) {
						if (randomBoolean == 1) {
							addMagnet();
						}
					}
				}

				if (timeSinceRuler >= 3) {
					timeSinceRuler = 0;
					addRuler();
				}
			}
		} else {
			RM.died.play();
			RM.coins = coins;
			RM.score = score;
			changeSceneListener.changeScene("mathScene");
		}
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// if(button == retry){
		if (changeSceneListener != null) {
			RM.buttonClicked.play();
		}
		// }
	}

	public void setChangeSceneListener(ChangeSceneListener listener) {
		changeSceneListener = listener;
	}

	public void setRM(ResourceManager rm) {
		RM = rm;
	}
}