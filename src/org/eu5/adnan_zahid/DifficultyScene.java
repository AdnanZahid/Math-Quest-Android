package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class DifficultyScene implements OnClickListener {

	private AnimatedButtonSprite back;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	
	private Sprite blackboardSprite;
	private Text difficultyLabel, inputNumber, correctNumber, remainingNumber, remainingNumber2;
	
	public void initComponents() {
		
		blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.blackboardTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);
		
		difficultyLabel = new Text(RM.WIDTH*0.3f, RM.HEIGHT*0.85f, RM.dusterFont, "Difficulty:", RM.getVertexBufferObjectManager());

		back = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Back", RM.buttonFont);
		back.setScale(scaleX, scaleY);
		back.setPosition(RM.WIDTH-back.getWidth(), back.getHeight()*1.2f);
		back.setOnClickListener(this);
		
		inputNumber = new Text(RM.WIDTH*0.22f, RM.HEIGHT*0.65f, RM.scoreFont, "▢ Cake", RM.getVertexBufferObjectManager());
		
		correctNumber = new Text(RM.WIDTH*0.22f, RM.HEIGHT*0.5f, RM.scoreFont, "☑ Medium", RM.getVertexBufferObjectManager());
		
		remainingNumber = new Text(RM.WIDTH*0.22f, RM.HEIGHT*0.35f, RM.scoreFont, "▢ Hard", RM.getVertexBufferObjectManager());

		remainingNumber2 = new Text(RM.WIDTH*0.22f, RM.HEIGHT*0.2f, RM.scoreFont, "▢ Geek", RM.getVertexBufferObjectManager());
	}
	
	public void populateIndividualScene(Scene scene) throws IOException {
		scene.attachChild(blackboardSprite);
		scene.attachChild(difficultyLabel);
		scene.attachChild(back);
		scene.registerTouchArea(back);
		scene.attachChild(inputNumber);
		scene.attachChild(correctNumber);
		scene.attachChild(remainingNumber);
		scene.attachChild(remainingNumber2);
	}
	
	public void unpopulateIndividualScene(Scene scene) throws IOException {
		scene.detachChild(blackboardSprite);
		scene.detachChild(difficultyLabel);
		scene.detachChild(back);
		scene.unregisterTouchArea(back);
		scene.detachChild(inputNumber);
		scene.detachChild(correctNumber);
		scene.detachChild(remainingNumber);
		scene.detachChild(remainingNumber2);
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(changeSceneListener!=null){
			if(button == back){
				changeSceneListener.changeScene("menuScene");
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
