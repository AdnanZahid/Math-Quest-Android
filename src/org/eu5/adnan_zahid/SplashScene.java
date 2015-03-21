package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class SplashScene {
	
	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;

	public void populateIndividualScene(Scene scene) throws IOException {
		
		Sprite blackboardSprite = new Sprite(RM.WIDTH/2, RM.HEIGHT/2, RM.splashTR, RM.getVertexBufferObjectManager());
		float scaleX = RM.WIDTH/blackboardSprite.getWidth();
		float scaleY = RM.HEIGHT/blackboardSprite.getHeight();
		blackboardSprite.setScale(scaleX, scaleY);
		scene.attachChild(blackboardSprite);
		
		RM.getEngine().registerUpdateHandler(new TimerHandler(2, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(changeSceneListener != null)
					changeSceneListener.changeScene("homeScene");
			}
		}));
	}

	public void setChangeSceneListener(ChangeSceneListener listener){
		changeSceneListener = listener;
	}
	
	public void setRM(ResourceManager rm){
		RM = rm;
	}
	
}
