package org.eu5.adnan_zahid.scenes.transitions;

import org.andengine.entity.scene.Scene;

/**
 *
 * @author rkpost
 */
public interface ITransitionListener {

    public void onTransitionFinished(Scene pOutScene, Scene pInScene, AbstractTransition pTransition);
    
}
