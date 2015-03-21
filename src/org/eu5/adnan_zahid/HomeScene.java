package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class HomeScene implements OnClickListener, IOnAreaTouchListener {

	private AnimatedButtonSprite guide, menu, facebook, twitter;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	
	private Sprite blackboardSprite;
	
	public void initComponents(){
		blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.backgroundTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);

		guide = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Start", RM.buttonFont);
		guide.setScale(scaleX*2f, scaleY*2.5f);
		guide.setPosition(guide.getWidth()*0.65f,RM. HEIGHT*0.62f);
		guide.setOnClickListener(this);

		menu = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Menu", RM.buttonFont);
		menu.setScale(scaleX*2f, scaleY*2.5f);
		menu.setPosition(guide.getWidth()*0.65f, menu.getHeight()*0.75f);
		menu.setOnClickListener(this);

		facebook = new AnimatedButtonSprite(0 , 0, RM.facebookTR, RM.getVertexBufferObjectManager(), "", RM.buttonFont);
		facebook.setScale(scaleX*1.5f, scaleY);
		facebook.setPosition(RM.WIDTH-guide.getWidth()*0.55f, RM.HEIGHT*0.62f);
		facebook.setOnClickListener(this);

		twitter = new AnimatedButtonSprite(0 , 0, RM.twitterTR, RM.getVertexBufferObjectManager(), "", RM.buttonFont);
		twitter.setScale(scaleX*1.5f, scaleY);
		twitter.setPosition(RM.WIDTH-guide.getWidth()*0.55f, menu.getHeight()*0.75f);
		twitter.setOnClickListener(this);
	}
	
	public void populateIndividualScene(Scene scene) throws IOException {
		
		scene.attachChild(blackboardSprite);
//		scene.registerTouchArea(blackboardSprite);
//		scene.setOnAreaTouchListener(this);

		scene.attachChild(guide);
		scene.registerTouchArea(guide);
		guide.setOnClickListener(this);

		scene.attachChild(menu);
		scene.registerTouchArea(menu);
		menu.setOnClickListener(this);

		scene.attachChild(facebook);
		scene.registerTouchArea(facebook);
		facebook.setOnClickListener(this);

		scene.attachChild(twitter);
		scene.registerTouchArea(twitter);
		twitter.setOnClickListener(this);
	}

	public void unpopulateIndividualScene(Scene scene){
		
		scene.detachChild(blackboardSprite);
//		scene.unregisterTouchArea(blackboardSprite);

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
	public void onClick(ButtonSprite button, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(changeSceneListener!=null){
			if(button == guide){
				changeSceneListener.changeScene("gameScene");
			}
			else if(button == menu){
				changeSceneListener.changeScene("menuScene");
			}
			else if(button == facebook){
			}
			else if(button == twitter){
			}
		}
	}
	
	public void setChangeSceneListener(ChangeSceneListener listener){
		changeSceneListener = listener;
	}

	public void setRM(ResourceManager rm){
		RM = rm;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		changeSceneListener.changeScene("gameScene");
		return false;
	}

}