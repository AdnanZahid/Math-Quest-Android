package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class AudioScene implements OnClickListener {

	private AnimatedButtonSprite sound, music, back;
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	
	private Sprite blackboardSprite;
	private Text audioLabel;
	
	public void initComponents(){
		blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.blackboardTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);
		
		audioLabel = new Text(RM.WIDTH*0.2f, RM.HEIGHT*0.85f,RM.dusterFont, "Audio:", RM.getVertexBufferObjectManager());

		sound = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Sound is on", RM.buttonFont);
		sound.setScale(scaleX*1.5f, scaleY);
		sound.setPosition(sound.getWidth()*1.5f, RM.HEIGHT-sound.getHeight()*3);
		sound.setOnClickListener(this);

		music = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Music is on", RM.buttonFont);
		music.setScale(scaleX*1.5f, scaleY);
		music.setPosition(music.getWidth()*1.5f, music.getHeight()*1.2f);
		music.setOnClickListener(this);

		back = new AnimatedButtonSprite(0 , 0, RM.buttonTR, RM.getVertexBufferObjectManager(), "Back", RM.buttonFont);
		back.setScale(scaleX, scaleY);
		back.setPosition(RM.WIDTH-back.getWidth(), back.getHeight()*1.2f);
		back.setOnClickListener(this);
	}
	
	public void populateIndividualScene(Scene scene) throws IOException {
		
		scene.attachChild(blackboardSprite);
		
		scene.attachChild(audioLabel);

		scene.attachChild(sound);
		scene.registerTouchArea(sound);

		scene.attachChild(music);
		scene.registerTouchArea(music);

		scene.attachChild(back);
		scene.registerTouchArea(back);
	}
	
	public void unpopulateIndividualScene(Scene scene){
		scene.detachChild(blackboardSprite);
		
		scene.detachChild(audioLabel);

		scene.detachChild(sound);
		scene.unregisterTouchArea(sound);

		scene.detachChild(music);
		scene.unregisterTouchArea(music);

		scene.detachChild(back);
		scene.unregisterTouchArea(back);
		
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if(changeSceneListener!=null){
			if(button == sound){
			}
			else if(button == music){
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
