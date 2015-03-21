//package org.eu5.adnan_zahid.scenes;
//
//import java.util.ArrayList;
//
//import javax.microedition.khronos.opengles.GL10;
//
//import org.andengine.engine.camera.Camera;
//import org.andengine.entity.IEntity;
//import org.andengine.entity.scene.Scene;
//import org.andengine.opengl.util.GLHelper;
//
///**
// *
// * @author rkpost
// */
//public class SceneBase extends Scene {
//
//    public SceneBase(int pLayerCount) {
//        super(pLayerCount);
//    }
//
//    @Override
//    protected void onManagedDraw(final GL10 pGL, final Camera pCamera) {
//        final Scene childScene = this.mChildScene;
//
//        if (childScene == null || !this.mChildSceneModalDraw) {
//            if (this.isBackgroundEnabled()) {
//
//                pCamera.onApplyMatrix(pGL);
//                GLHelper.setModelViewIdentityMatrix(pGL);
//
//                pGL.glPushMatrix();
//                {
//                    this.onApplyTransformations(pGL);
//                    this.getBackground().onDraw(pGL, pCamera);
//                }
//                pGL.glPopMatrix();
//
//            }
//
//            pCamera.onApplyMatrix(pGL);
//            GLHelper.setModelViewIdentityMatrix(pGL);
//
//            pGL.glPushMatrix();
//            {
//                this.onApplyTransformations(pGL);
//
//                this.doDraw(pGL, pCamera);
//
//                if (this.mChildren != null) {
//                    final ArrayList<IEntity> entities = this.mChildren;
//                    final int entityCount = entities.size();
//                    for (int i = 0; i < entityCount; i++) {
//                        entities.get(i).onDraw(pGL, pCamera);
//                    }
//                }
//            }
//            pGL.glPopMatrix();
//
//        }
//
//        if (childScene != null) {
//            childScene.onDraw(pGL, pCamera);
//        }
//    }
//}
