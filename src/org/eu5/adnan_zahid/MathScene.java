package org.eu5.adnan_zahid;

import java.io.IOException;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import android.graphics.Color;

public class MathScene implements OnClickListener, IOnSceneTouchListener {

	private ResourceManager RM;
	private ChangeSceneListener changeSceneListener;
	private Scene scene;

	private Sprite blackboardSprite;
	private AnimatedButtonSprite retry, button1, button2, button3, button4,
			button5, button6, button7, button8, button9, button0, scores;

	private float numberOfInputs;

	private float scaleX = 0;
	private float scaleY = 0;

	private int sum;

	public void initComponents(Scene scene) {

		this.scene = scene;

		blackboardSprite = new Sprite(RM.WIDTH / 2, RM.HEIGHT / 2,
				RM.blackboardTR, RM.getVertexBufferObjectManager());
		scaleX = RM.WIDTH / blackboardSprite.getWidth();
		scaleY = RM.HEIGHT / blackboardSprite.getHeight();
		blackboardSprite.setScaleX(scaleX);
		blackboardSprite.setScaleY(scaleY);

		button1 = new AnimatedButtonSprite(0, 0, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "1", RM.mathButtonFont);
		button1.setScaleX(scaleX * 0.3f);
		button1.setScaleY(scaleY * 0.65f);
		button1.setPosition(button1.getWidth() * button1.getScaleX(), RM.HEIGHT
				- button1.getHeight() * button1.getScaleY() * 3);

		button2 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX() * 2, RM.HEIGHT - button1.getHeight()
				* button1.getScaleY() * 3, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "2", RM.mathButtonFont);
		button2.setScaleX(scaleX * 0.3f);
		button2.setScaleY(scaleY * 0.65f);

		button3 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX() * 3, RM.HEIGHT - button1.getHeight()
				* button1.getScaleY() * 3, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "3", RM.mathButtonFont);
		button3.setScaleX(scaleX * 0.3f);
		button3.setScaleY(scaleY * 0.65f);

		button4 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX(), RM.HEIGHT - button1.getHeight()
				* button1.getScaleY() * 2, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "4", RM.mathButtonFont);
		button4.setScaleX(scaleX * 0.3f);
		button4.setScaleY(scaleY * 0.65f);

		button5 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX() * 2, RM.HEIGHT - button1.getHeight()
				* button1.getScaleY() * 2, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "5", RM.mathButtonFont);
		button5.setScaleX(scaleX * 0.3f);
		button5.setScaleY(scaleY * 0.65f);

		button6 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX() * 3, RM.HEIGHT - button1.getHeight()
				* button1.getScaleY() * 2, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "6", RM.mathButtonFont);
		button6.setScaleX(scaleX * 0.3f);
		button6.setScaleY(scaleY * 0.65f);

		button7 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX(), RM.HEIGHT - button1.getHeight()
				* button1.getScaleY(), RM.buttonTR,
				RM.getVertexBufferObjectManager(), "7", RM.mathButtonFont);
		button7.setScaleX(scaleX * 0.3f);
		button7.setScaleY(scaleY * 0.65f);

		button8 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX() * 2, RM.HEIGHT - button1.getHeight()
				* button1.getScaleY(), RM.buttonTR,
				RM.getVertexBufferObjectManager(), "8", RM.mathButtonFont);
		button8.setScaleX(scaleX * 0.3f);
		button8.setScaleY(scaleY * 0.65f);

		button9 = new AnimatedButtonSprite(button1.getWidth()
				* button1.getScaleX() * 3, RM.HEIGHT - button1.getHeight()
				* button1.getScaleY(), RM.buttonTR,
				RM.getVertexBufferObjectManager(), "9", RM.mathButtonFont);
		button9.setScaleX(scaleX * 0.3f);
		button9.setScaleY(scaleY * 0.65f);

		button0 = new AnimatedButtonSprite(0, 0, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "0", RM.mathButtonFont);
		button0.setScaleX(scaleX * 0.6f);
		button0.setScaleY(scaleY * 0.65f);
		button0.setPosition(button0.getWidth() * button0.getScaleX() * 1.25f,
				RM.HEIGHT - button0.getHeight() * button0.getScaleY() * 4);

		retry = new AnimatedButtonSprite(0, 0, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "Retry", RM.mathButtonFont);
		retry.setScaleX(scaleX * 0.7f);
		retry.setScaleY(scaleY * 0.65f);
		retry.setPosition(RM.WIDTH - retry.getWidth() * retry.getScaleX()
				* 0.75f, RM.HEIGHT - retry.getHeight() * retry.getScaleY());
		retry.setOnClickListener(this);
		retry.setVisible(false);

		scores = new AnimatedButtonSprite(0, 0, RM.buttonTR,
				RM.getVertexBufferObjectManager(), "Scores", RM.mathButtonFont);
		scores.setScaleX(scaleX * 0.7f);
		scores.setScaleY(scaleY * 0.65f);
		scores.setPosition(RM.WIDTH - scores.getWidth() * scores.getScaleX()
				* 0.75f, RM.HEIGHT - scores.getHeight() * scores.getScaleY()
				* 4.92f);
		scores.setVisible(false);
	}

	public void populateIndividualScene() throws IOException {

		numberOfInputs = 0;
		sum = 0;
        
        if(RM.score / 100000 > 0) {
            numberOfInputs = 0;
        }
        else if(RM.score / 10000 > 0) {
            numberOfInputs = 1;
        }
        else if(RM.score / 1000 > 0) {
            numberOfInputs = 2;
        }
        else if(RM.score / 100 > 0) {
            numberOfInputs = 3;
        }
        else if(RM.score / 10 > 0) {
            numberOfInputs = 4;
        }
        else if(RM.score >= 0) {
            numberOfInputs = 5;
        }

		scene.attachChild(blackboardSprite);
		scene.attachChild(button1);
		scene.registerTouchArea(button1);
		button1.setOnClickListener(this);
		scene.attachChild(button2);
		scene.registerTouchArea(button2);
		button2.setOnClickListener(this);
		scene.attachChild(button3);
		scene.registerTouchArea(button3);
		button3.setOnClickListener(this);
		scene.attachChild(button4);
		scene.registerTouchArea(button4);
		button4.setOnClickListener(this);
		scene.attachChild(button5);
		scene.registerTouchArea(button5);
		button5.setOnClickListener(this);
		scene.attachChild(button6);
		scene.registerTouchArea(button6);
		button6.setOnClickListener(this);
		scene.attachChild(button7);
		scene.registerTouchArea(button7);
		button7.setOnClickListener(this);
		scene.attachChild(button8);
		scene.registerTouchArea(button8);
		button8.setOnClickListener(this);
		scene.attachChild(button9);
		scene.registerTouchArea(button9);
		button9.setOnClickListener(this);
		scene.attachChild(button0);
		scene.registerTouchArea(button0);
		button0.setOnClickListener(this);
		scene.attachChild(retry);
		scene.registerTouchArea(retry);
		retry.setOnClickListener(this);
		scene.attachChild(scores);
		scene.registerTouchArea(scores);
		scores.setOnClickListener(this);
	}

	public void unpopulateIndividualScene(Scene scene) throws IOException {

		scene.detachChild(blackboardSprite);
		scene.detachChild(button1);
		scene.unregisterTouchArea(button1);
		scene.detachChild(button2);
		scene.unregisterTouchArea(button2);
		scene.detachChild(button3);
		scene.unregisterTouchArea(button3);
		scene.detachChild(button4);
		scene.unregisterTouchArea(button4);
		scene.detachChild(button5);
		scene.unregisterTouchArea(button5);
		scene.detachChild(button6);
		scene.unregisterTouchArea(button6);
		scene.detachChild(button7);
		scene.unregisterTouchArea(button7);
		scene.detachChild(button8);
		scene.unregisterTouchArea(button8);
		scene.detachChild(button9);
		scene.unregisterTouchArea(button9);
		scene.detachChild(button0);
		scene.unregisterTouchArea(button0);
		scene.detachChild(retry);
		scene.unregisterTouchArea(retry);
		scene.detachChild(scores);
		scene.unregisterTouchArea(scores);
	}
	
	private void placeLabel(String text, float distance, float yCoefficient) {
		float xCoefficient = 0.63f;
		Text label = new Text(
				RM.WIDTH * xCoefficient - distance * 31 * scaleX,
				RM.HEIGHT - yCoefficient * 40 * scaleY - 30 * retry.getScaleY(),
				RM.mathDusterFont, text,
				new TextOptions(HorizontalAlign.RIGHT), RM
						.getVertexBufferObjectManager());
		label.setScaleX(scaleX);
		label.setScaleY(scaleY);
		scene.attachChild(label);
	}

	private void placeLabel(String text, float distance, float yCoefficient, int color) {
		float xCoefficient = 0.63f;
		Text label = new Text(
				RM.WIDTH * xCoefficient - distance * 31 * scaleX,
				RM.HEIGHT - yCoefficient * 40 * scaleY - 30 * retry.getScaleY(),
				RM.mathDusterFont, text,
				new TextOptions(HorizontalAlign.RIGHT), RM
						.getVertexBufferObjectManager());
		label.setScaleX(scaleX);
		label.setScaleY(scaleY);
		
		label.setColor(color);
		
		scene.attachChild(label);
	}

	private int decimalPlaces(int maxNumberLength, int whichNumber) {
		int resultingNumber = 0;
		if (whichNumber >= 100000) {
			resultingNumber = maxNumberLength - 5;
		} else if (whichNumber >= 10000) {
			resultingNumber = maxNumberLength - 4;
		} else if (whichNumber >= 1000) {
			resultingNumber = maxNumberLength - 3;
		} else if (whichNumber >= 100) {
			resultingNumber = maxNumberLength - 2;
		} else if (whichNumber >= 10) {
			resultingNumber = maxNumberLength - 1;
		} else {
			resultingNumber = maxNumberLength;
		}
		return resultingNumber;
	}

	private void numberPressed(int number) {

		if (numberOfInputs < 6) {

			numberOfInputs++;

			float distance = 7 - numberOfInputs;
			placeLabel(Integer.toString(number), distance, 1.5f);

			double numberPlace = Math.pow(10, 6 - numberOfInputs);
			double correctNumber = (int) ((int)RM.score % (numberPlace * 10)
					/ (numberPlace));

			int processedScore = 0;
			if (number == correctNumber) {
				placeLabel(Integer.toString((int) correctNumber), distance, 3, Color.GREEN);
				processedScore = number;
				placeLabel(Integer.toString((int) number), distance, 4.5f);
			} else {
				placeLabel(Integer.toString((int) correctNumber), distance, 3, Color.RED);
				processedScore = 0;
				placeLabel("0", distance, 4.5f);
			}
			sum += processedScore * numberPlace;

			if (numberOfInputs == 6) {

				placeLabel("(Remains)", distance - 6, 4.5f);

				int resultingNumber = decimalPlaces(6, sum);

				for (int i = resultingNumber; i <= 6; i++) {
					numberPlace = Math.pow(10, 6 - i);
					correctNumber = (sum % (numberPlace * 10)) / (numberPlace);

					placeLabel(Integer.toString((int) correctNumber), 7 - i, 6);
				}

				resultingNumber = decimalPlaces(5, RM.coins);

				for (int i = resultingNumber; i <= 5; i++) {
					numberPlace = Math.pow(10, 5 - i);
					correctNumber = (RM.coins % (numberPlace * 10))
							/ (numberPlace);

					placeLabel(Integer.toString((int) correctNumber), -i, 6);
				}

				placeLabel(" + ", distance - 1, 6);

				sum += RM.coins;
				resultingNumber = decimalPlaces(6, sum);

				for (int i = resultingNumber; i <= 6; i++) {
					numberPlace = Math.pow(10, 6 - i);
					correctNumber = (sum % (numberPlace * 10)) / (numberPlace);

					placeLabel(Integer.toString((int) correctNumber), 7 - i,
							7.5f);
				}

				placeLabel("(Total)", distance - 6, 7.5f);
				
				retry.setVisible(true);
				scores.setVisible(true);
			}
		}
	}

	@Override
	public void onClick(ButtonSprite button, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if (changeSceneListener != null) {
			if (button == retry) {
				changeSceneListener.changeScene("gameScene");
			} else if (button == scores) {
				changeSceneListener.changeScene("highscoreScene");
			} else if (button == button0) {
				numberPressed(0);
			} else if (button == button1) {
				numberPressed(1);
			} else if (button == button2) {
				numberPressed(2);
			} else if (button == button3) {
				numberPressed(3);
			} else if (button == button4) {
				numberPressed(4);
			} else if (button == button5) {
				numberPressed(5);
			} else if (button == button6) {
				numberPressed(6);
			} else if (button == button7) {
				numberPressed(7);
			} else if (button == button8) {
				numberPressed(8);
			} else if (button == button9) {
				numberPressed(9);
			}
		}
	}

	public void setChangeSceneListener(ChangeSceneListener listener) {
		changeSceneListener = listener;
	}

	public void setRM(ResourceManager rm) {
		RM = rm;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}

}
