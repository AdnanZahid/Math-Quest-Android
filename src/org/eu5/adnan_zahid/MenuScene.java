package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class MenuScene implements OnClickListener {

	private AnimatedButtonSprite audio, highscores, difficulty, back;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	
	private Sprite blackboardSprite;
	private Text menuLabel;
	
	public void initComponents(){
		blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.blackboardTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);
		
		menuLabel = new Text(RM.WIDTH*0.2f, RM.HEIGHT*0.85f, RM.dusterFont, "Menu:", RM.getVertexBufferObjectManager());

		audio = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Audio", RM.buttonFont);
		audio.setScale(scaleX, scaleY);
		audio.setPosition(audio.getWidth()*1.65f, RM.HEIGHT-audio.getHeight()*2.45f);
		audio.setOnClickListener(this);

		highscores = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Highscores", RM.buttonFont);
		highscores.setScale(scaleX, scaleY);
		highscores.setPosition(RM.WIDTH-highscores.getWidth()*1.65f, RM.HEIGHT-highscores.getHeight()*2.45f);
		highscores.setOnClickListener(this);

		difficulty = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Difficulty", RM.buttonFont);
		difficulty.setScale(scaleX, scaleY);
		difficulty.setPosition(difficulty.getWidth()*1.65f, difficulty.getHeight()*1.65f);
		difficulty.setOnClickListener(this);

		back = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Back", RM.buttonFont);
		back.setScale(scaleX, scaleY);
		back.setPosition(RM.WIDTH-back.getWidth()*1.65f, back.getHeight()*1.65f);
		back.setOnClickListener(this);
		
	}
	
	public void populateIndividualScene(Scene scene) throws IOException {
		
		scene.attachChild(blackboardSprite);
		
		scene.attachChild(menuLabel);

		scene.attachChild(audio);
		scene.registerTouchArea(audio);
		audio.setOnClickListener(this);

		scene.attachChild(highscores);
		scene.registerTouchArea(highscores);
		highscores.setOnClickListener(this);

		scene.attachChild(difficulty);
		scene.registerTouchArea(difficulty);
		difficulty.setOnClickListener(this);

		scene.attachChild(back);
		scene.registerTouchArea(back);
		back.setOnClickListener(this);
	}
	
	public void unpopulateIndividualScene(Scene scene){
		
		scene.detachChild(blackboardSprite);
		
		scene.detachChild(menuLabel);

		scene.detachChild(audio);
		scene.unregisterTouchArea(audio);

		scene.detachChild(highscores);
		scene.unregisterTouchArea(highscores);

		scene.detachChild(difficulty);
		scene.unregisterTouchArea(difficulty);

		scene.detachChild(back);
		scene.unregisterTouchArea(back);
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if(changeSceneListener!=null){
			if(button == audio) {
				changeSceneListener.changeScene("audioScene");
			}
			else if(button == highscores){
				changeSceneListener.changeScene("highscoresScene");
			}
			else if(button == difficulty){
				changeSceneListener.changeScene("difficultyScene");
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
