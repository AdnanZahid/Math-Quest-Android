package org.eu5.adnan_zahid.scenes.transitions;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.IEaseFunction;
import org.eu5.adnan_zahid.WindowManager;

/**
 *
 * @author rkpost
 */
public class LeftPushInTransition extends AbstractTransition {

    public LeftPushInTransition(float pDuration) {
        super(pDuration);
    }

    public LeftPushInTransition(float mDuration, IEaseFunction pEaseFunction) {
        super(mDuration, pEaseFunction);
    }

    @Override
    public void execute(final Scene pOutScene, final Scene pInScene, final ITransitionListener pTransitionListener) {

        float width = WindowManager.getInstance().getSurfaceWidth();

        MoveXModifier outModifier = new MoveXModifier(1, 0, width, mEaseFunction);
        MoveXModifier inModifier = new MoveXModifier(1, -width, 0, mEaseFunction);

        pInScene.registerEntityModifier(inModifier);
        pOutScene.registerEntityModifier(outModifier);

        inModifier.addModifierListener(new IModifierListener<IEntity>() {

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                onTransitionFinish(pOutScene, pInScene, pTransitionListener);
            }

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
        });

    }

    @Override
    public AbstractTransition getBackTransition() {
        return this;
    }
}
