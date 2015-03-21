package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class HighscoresScene implements OnClickListener {

	private AnimatedButtonSprite gameCenter, back;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	
	private Sprite blackboardSprite;
	private Text highscoresLabel, inputNumber, correctNumber, remainingNumber, remainingNumber2, coins;
	
	public void initComponents() {
		blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.blackboardTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);
		
		highscoresLabel = new Text(RM.WIDTH*0.3f, RM.HEIGHT*0.85f, RM.dusterFont, "Highscores:", RM.getVertexBufferObjectManager());

		gameCenter = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Game Center", RM.buttonFont);
		gameCenter.setScale(scaleX, scaleY);
		gameCenter.setPosition(RM.WIDTH-gameCenter.getWidth(), RM.HEIGHT/2);
		gameCenter.setOnClickListener(this);

		back = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Back", RM.buttonFont);
		back.setScale(scaleX, scaleY);
		back.setPosition(RM.WIDTH-back.getWidth(), back.getHeight()*1.2f);
		back.setOnClickListener(this);
		
		inputNumber = new Text(RM.WIDTH*0.1f, RM.HEIGHT*0.7f, RM.scoreFont, "1)", RM.getVertexBufferObjectManager());
		
		correctNumber = new Text(RM.WIDTH*0.1f, RM.HEIGHT*0.56f, RM.scoreFont, "2)", RM.getVertexBufferObjectManager());
		
		remainingNumber = new Text(RM.WIDTH*0.1f, RM.HEIGHT*0.42f, RM.scoreFont, "3)", RM.getVertexBufferObjectManager());

		remainingNumber2 = new Text(RM.WIDTH*0.1f, RM.HEIGHT*0.28f, RM.scoreFont, "4)", RM.getVertexBufferObjectManager());
		
		coins = new Text(RM.WIDTH*0.1f, RM.HEIGHT*0.14f, RM.scoreFont, "5)", RM.getVertexBufferObjectManager());
	}
	
	public void populateIndividualScene(Scene scene) throws IOException {

		scene.attachChild(blackboardSprite);
		scene.attachChild(highscoresLabel);
		scene.attachChild(gameCenter);
		scene.registerTouchArea(gameCenter);
		scene.attachChild(back);
		scene.registerTouchArea(back);
		scene.attachChild(inputNumber);
		scene.attachChild(correctNumber);
		scene.attachChild(remainingNumber);
		scene.attachChild(remainingNumber2);
		scene.attachChild(coins);
	}
	
	public void unpopulateIndividualScene(Scene scene) throws IOException {

		scene.detachChild(blackboardSprite);
		scene.detachChild(highscoresLabel);
		scene.detachChild(gameCenter);
		scene.unregisterTouchArea(gameCenter);
		scene.detachChild(back);
		scene.unregisterTouchArea(back);
		scene.detachChild(inputNumber);
		scene.detachChild(correctNumber);
		scene.detachChild(remainingNumber);
		scene.detachChild(remainingNumber2);
		scene.detachChild(coins);
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(changeSceneListener!=null){
			if(button == gameCenter){
			}
			else if(button == back){
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
