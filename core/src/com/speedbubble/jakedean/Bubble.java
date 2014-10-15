package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/** Class to be used in Arcade mode right now. will replace the need for bubble and phantom bubble in assets eventually
 * 
 * @author Dean
 *
 */
public class Bubble {
	
	public int amplitude, frequency, column;
	public float waveBegin, xPosition, yPosition, sideLength, originalXPosition;
	public Sprite background, face;
	
	
	public Bubble(){
		/** instantiates all of the variables above
		 * 
		 * the goal here is to make it so each bubble moves in a unique path in arcade mode
		 * waveBegin = place inside a sin wave where movement will begin
		 * amplitude = amplitude of a sin wave
		 * frequency = frequency of a sin wave
		 * column = column the bubble will move down
		 * xPosition = x-position of the bubble
		 * yPosition = y-position of the bubble
		 * sideLength = sideLength of the bubble (the sprite is a square)
		 * originalXPosition = the reference x-position to use when moving the bubble in a sinusoidal motion
		 * 
		 * background = the image of the bubble
		 * face = the image of the face
		 */
		waveBegin = 0;
		amplitude = 0;
		frequency = 0;
		column = 0;
		xPosition = 0;
		yPosition = 0;
		sideLength = Gdx.graphics.getWidth()/5;
		originalXPosition = 0;
		
		background = new Sprite(Assets.bubbleAtlas.findRegion("bubble0"));
		background.setSize(sideLength, sideLength);
		int determineFace = MathUtils.random(1, 5);
		face = new Sprite(Assets.faceAtlas.findRegion("face"+determineFace));
		face.setSize(sideLength, sideLength);
		
	}
	
	public void spawn(int cols){
	//determine the column the bubble will travel down
		column = MathUtils.random(0,cols-1);
		
	//assign reference x-position based on column number, yPosition always top of screen
		originalXPosition = column*sideLength;
		yPosition = Gdx.graphics.getHeight();
		
	// randomize the sin wave to make sure no bubble moves in an identical pattern
		waveBegin = MathUtils.random(-3, 3);
		amplitude = MathUtils.random(0, 50);
		frequency = MathUtils.random(1, 5);
		switch(frequency){
		case 1:
			frequency = 0;
			break;
		case 2:
			frequency = 5;
			break;
		case 3: 
			frequency = 10;
			break;
		case 4:
			frequency = 15;
			break;
		case 5:
			frequency = 20;
			break;
		}
		
	//the sprites are assigned an image that was created in Assets class
		background.setRegion(Assets.bubbleAtlas.findRegion("bubble0"));
		background.setPosition(originalXPosition, yPosition);
		face.setPosition(originalXPosition, yPosition);
		
	}
	
	/** In Arcade mode, xPosition is changed based on the sin wave motion, and the yPosition is changed based on
	 *  the speed of the bubble
	 *  
	 *   this method will make sure the face and background stay bound together
	 * 
	 */
	public void updatePosition(){
		background.setPosition(xPosition, yPosition);
		face.setPosition(xPosition, yPosition);
	}

	public static void moveBubbles(Bubble previous, Bubble current, Bubble next){
		previous.xPosition = current.xPosition;
		previous.yPosition = current.yPosition;
		previous.updatePosition();
		
		current.xPosition = next.xPosition;
		current.yPosition = next.yPosition;
		current.updatePosition();
		
		next.xPosition = MathUtils.random(0, Gdx.graphics.getWidth() - next.sideLength);
		next.yPosition = MathUtils.random(0, Gdx.graphics.getHeight() - 2*next.sideLength);
		next.updatePosition();
	}
	
	public void removeFace(){
		face.setRegion(Assets.faceAtlas.findRegion("face0"));
	}
}
