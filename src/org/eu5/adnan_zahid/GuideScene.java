package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class GuideScene implements OnClickListener {

	private AnimatedButtonSprite tutorial, back;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	
	private Sprite blackboardSprite;
	private Text guideLabel, inputNumber, correctNumber, remainingNumber, coins;
	
	public void initComponents() {
		
		blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.blackboardTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);
		
		guideLabel = new Text(RM.WIDTH*0.22f, RM.HEIGHT*0.85f, RM.dusterFont, "Guide:", RM.getVertexBufferObjectManager());

		back = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Back", RM.buttonFont);
		back.setScale(scaleX, scaleY);
		back.setPosition(RM.WIDTH-back.getWidth(), back.getHeight()*1.2f);
		back.setOnClickListener(this);
		
		inputNumber = new Text(RM.WIDTH*0.4f, RM.HEIGHT*0.7f, RM.scoreFont, "1) Tap the screen to fly.", RM.getVertexBufferObjectManager());
		
		correctNumber = new Text(RM.WIDTH*0.4f, RM.HEIGHT*0.56f, RM.scoreFont, "2) Collect numbers and coins.", RM.getVertexBufferObjectManager());
		
		remainingNumber = new Text(RM.WIDTH*0.4f, RM.HEIGHT*0.42f, RM.scoreFont, "3) Calculate your own score.", RM.getVertexBufferObjectManager());
		
		coins = new Text(RM.WIDTH*0.1f, RM.HEIGHT*0.14f, RM.scoreFont, "5)", RM.getVertexBufferObjectManager());

		tutorial = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Tutorial", RM.buttonFont);
		tutorial.setScale(scaleX*1.5f, scaleY);
		tutorial.setPosition(tutorial.getWidth()*1.5f, tutorial.getHeight()*1.2f);
		tutorial.setOnClickListener(this);
	}

	public void populateIndividualScene(Scene scene) throws IOException {
		scene.attachChild(blackboardSprite);
		scene.attachChild(guideLabel);
		scene.attachChild(back);
		scene.registerTouchArea(back);
		scene.attachChild(inputNumber);
		scene.attachChild(correctNumber);
		scene.attachChild(remainingNumber);
		scene.attachChild(coins);
		scene.attachChild(tutorial);
		scene.registerTouchArea(tutorial);
	}

	public void unpopulateIndividualScene(Scene scene) throws IOException {
		scene.detachChild(blackboardSprite);
		scene.detachChild(guideLabel);
		scene.detachChild(back);
		scene.unregisterTouchArea(back);
		scene.detachChild(inputNumber);
		scene.detachChild(correctNumber);
		scene.detachChild(remainingNumber);
		scene.detachChild(coins);
		scene.detachChild(tutorial);
		scene.unregisterTouchArea(tutorial);
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(changeSceneListener!=null){
			RM.buttonClicked.play();
			if(button == tutorial){
			}
			else if(button == back){
				changeSceneListener.changeScene("homeScene");
			}
		}
	}

	public void setChangeSceneListener(ChangeSceneListener listener){
		changeSceneListener = listener;
	}
	
	public void setRM(ResourceManager rm){
		RM = rm;
	}

}
