package org.eu5.adnan_zahid;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.eu5.adnan_zahid.scenes.transitions.AbstractTransition;
import org.eu5.adnan_zahid.scenes.transitions.ITransitionListener;

import android.util.DisplayMetrics;

/**
 *
 * @author rkpost
 */
public class WindowManager {

    private static WindowManager INSTANCE = new WindowManager();

    public static void initialize(BaseGameActivity pActivity) {
        INSTANCE.mDisplayMetrics = new DisplayMetrics();
        INSTANCE.mEngine = pActivity.getEngine();
        INSTANCE.mActivity = pActivity;
        INSTANCE.mActivity.getWindowManager().getDefaultDisplay().getMetrics(INSTANCE.mDisplayMetrics);
    }

    public static WindowManager getInstance() {
        return INSTANCE;
    }
    
    private BaseGameActivity mActivity;
    private DisplayMetrics mDisplayMetrics;
    private Engine mEngine;
    private Scene mTransitionScene = new Scene(0);

    public float getDisplayHeight() {
        return mDisplayMetrics.heightPixels * mDisplayMetrics.scaledDensity;
    }

    public float getDisplayWidth() {
        return mDisplayMetrics.widthPixels * mDisplayMetrics.scaledDensity;
    }

    public float getSurfaceHeight() {
        return mEngine.getCamera().getHeight();
    }

    public float getSurfaceWidth() {
        return mEngine.getCamera().getWidth();
    }

    public Scene getCurrentScene() {
        return mEngine.getScene();
    }

    public void setScene(final Scene pScene) {
        mEngine.runOnUpdateThread(new Runnable() {

            @Override
            public void run() {
                mEngine.setScene(pScene);
            }
        });
    }

    public void setScene(Scene pInScene, AbstractTransition pTransition) {

        this.setScene(mTransitionScene);
        mTransitionScene.attachChild(this.getCurrentScene());
        mTransitionScene.attachChild(pInScene);
        
        pTransition.execute(this.getCurrentScene(), pInScene, new ITransitionListener() {

            @Override
            public void onTransitionFinished(Scene pOutScene, Scene pInScene, AbstractTransition pTransition) {
                mEngine.runOnUpdateThread(new Runnable() {

                    @Override
                    public void run() {
                        mTransitionScene.detachChildren();
                    }
                });
                WindowManager.this.setScene(pInScene);
            }
        });

    }
}
