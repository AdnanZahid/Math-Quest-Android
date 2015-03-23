package org.eu5.adnan_zahid;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.graphics.Color;

public class HomeScene implements OnClickListener, IOnSceneTouchListener {

	private AnimatedButtonSprite guide, menu, facebook, twitter;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;

	private Sprite blackboardSprite;
	private Text tapScreenToPlay;

	public void initComponents() {
		blackboardSprite = new Sprite(RM.WIDTH / 2, RM.HEIGHT / 2,
				RM.backgroundTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH / blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT / blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);

		guide = new AnimatedButtonSprite(0, 0, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "Guide", RM.buttonFont);
		guide.setScale(scaleX * 2f, scaleY * 2.5f);
		guide.setPosition(guide.getWidth() * 0.65f, RM.HEIGHT * 0.62f);
		guide.setOnClickListener(this);

		menu = new AnimatedButtonSprite(0, 0, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "Menu", RM.buttonFont);
		menu.setScale(scaleX * 2f, scaleY * 2.5f);
		menu.setPosition(guide.getWidth() * 0.65f, menu.getHeight() * 0.75f);
		menu.setOnClickListener(this);

		facebook = new AnimatedButtonSprite(0, 0, RM.facebookTR,
				RM.getVertexBufferObjectManager(), "", RM.buttonFont);
		facebook.setScale(scaleX * 1.5f, scaleY);
		facebook.setPosition(RM.WIDTH - guide.getWidth() * 0.55f,
				RM.HEIGHT * 0.62f);
		facebook.setOnClickListener(this);

		twitter = new AnimatedButtonSprite(0, 0, RM.twitterTR,
				RM.getVertexBufferObjectManager(), "", RM.buttonFont);
		twitter.setScale(scaleX * 1.5f, scaleY);
		twitter.setPosition(RM.WIDTH - guide.getWidth() * 0.55f,
				menu.getHeight() * 0.75f);
		twitter.setOnClickListener(this);

		tapScreenToPlay = new Text(RM.WIDTH * 0.525f, RM.HEIGHT * 0.1f,
				RM.scoreFont, "Tap screen to play!",
				RM.getVertexBufferObjectManager());
		tapScreenToPlay.setColor(Color.LTGRAY);
	}

	public void populateIndividualScene(Scene scene) throws IOException {

		scene.attachChild(blackboardSprite);
		scene.setOnSceneTouchListener(this);

		scene.attachChild(guide);
		scene.registerTouchArea(guide);

		scene.attachChild(menu);
		scene.registerTouchArea(menu);

		scene.attachChild(facebook);
		scene.registerTouchArea(facebook);

		scene.attachChild(twitter);
		scene.registerTouchArea(twitter);

		RM.gameSceneSound.play();
		// RM.homeSceneSound.pause();
		// RM.mathSceneSound.pause();

		tapScreenToPlay.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		AlphaModifier fadeOut = new AlphaModifier(1f, 1f, 0);
		AlphaModifier fadeIn = new AlphaModifier(1f, 0, 1f);

		SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(
				fadeOut, fadeIn) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.reset();
			}
		};
		sequenceEntityModifier.setAutoUnregisterWhenFinished(false);
		tapScreenToPlay.registerEntityModifier(sequenceEntityModifier);

		scene.attachChild(tapScreenToPlay);
	}

	public void unpopulateIndividualScene(Scene scene) {

		scene.detachChild(blackboardSprite);

		scene.detachChild(guide);
		scene.unregisterTouchArea(guide);

		scene.detachChild(menu);
		scene.unregisterTouchArea(menu);

		scene.detachChild(facebook);
		scene.unregisterTouchArea(facebook);

		scene.detachChild(twitter);
		scene.unregisterTouchArea(twitter);
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if (changeSceneListener != null) {
			RM.buttonClicked.play();
			if (button == guide) {
				changeSceneListener.changeScene("guideScene");
			} else if (button == menu) {
				changeSceneListener.changeScene("menuScene");
			} else if (button == facebook) {
			} else if (button == twitter) {
			}
		}
	}

	public void setChangeSceneListener(ChangeSceneListener listener) {
		changeSceneListener = listener;
	}

	public void setRM(ResourceManager rm) {
		RM = rm;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		changeSceneListener.changeScene("gameScene");
		return false;
	}

}