package com.speedbubble.jakedean;


import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameModeArcade implements GameMode{
	
	private SpriteBatch batch;
	private Texture backgroundTexture, arcadeLine;
	private BitmapFont gameFont;
	private boolean popStarted, missedBubble, removeBubble;

	private Array<Sprite> bubbles;
	private Sprite leadBubble, background;
	private int bubblesPopped, cols, sideLength, bubbleColor, colorInterval;
	private float stateTime, speed, yPos, width, height;
	
	public GameModeArcade(){
		
		/** Initialize all of the generic images, text, and sprite batch
		 */
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("inGameBackground.png"));
		background = new Sprite(backgroundTexture);
		arcadeLine = new Texture(Gdx.files.internal("arcadeLine.png"));
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
        gameFont.setScale(1.5f);
    	gameFont.setColor(1, 1, 1, 1);
    	
    	/** Create flags
    	 * 
    	 * popStarted = used to determine when to draw a popping animation
    	 * missedBubble = used to determine if the player clicked outside of a bubble, will end game
    	 * removeBubble = used to determine when to remove a bubble from the screen and the bubble array
    	 */
    	popStarted = false;
    	missedBubble = false;
    	removeBubble = false;
    	
    	/** Variable initialization
    	 * 
    	 * stateTime = used to determine what frame to draw in the the popping animation
    	 * bubblesPopped = the score
    	 * width = width of the screen or device
    	 * height = height of the screen or device
    	 */
    	stateTime = 0;
    	bubblesPopped = 0;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		/** Used to determine the size of the background image for all screen sizes
		 * This is to ensure the background does not look stretched by maintaining the image's aspect ratio
		 */
		if(height/width > .5f){
			float ratio = height/1024;
			background.setSize(2048*ratio, height);
			background.setPosition((Gdx.graphics.getWidth()/2 - background.getWidth()/2), 0);
		}
		else if (height/width < .5f){
			float ratio = width/2048;
			background.setSize(width, 1024*ratio);
			background.setPosition(0, (Gdx.graphics.getHeight()/2 - background.getHeight()/2));
		}
		else if (height/width == .5f){
			background.setSize(width, height);
		}
		
		/** creates an array of bubbles and sets the spacing for the game. the array is used to minimize memory allocation
		 *       as we remove the index of a bubble from the array whenever it gets popped. normally maxes out at 3-4 bubbles
		 *       in the array at all times
		 * 
		 * bubbles = array of bubble sprites (images)
		 * cols = used to determine how many columns
		 * sideLength = sets the bubble to be contained inside a perfect square, the exact width of 1 column
		 */
		bubbles = new Array<Sprite>();
		cols = 5;
		sideLength = (int)(width/cols);
		
		/** Variables used to determine when to spawn the next bubble (a new bubble spawns at the top of the screen
		 *		as soon as the bubble before it makes it one side-length down the screen)
		 */
		speed = height/2;
		yPos = 0;
		
		/** Variables used to determine what color to make the bubbles as they are falling.
		 * 		the bubble color will alternate every 25th bubble 
		 */
		bubbleColor=Settings.favoriteColor;
		colorInterval=0;
		Assets.setBubbleColor(bubbleColor);
		
		/** spawns the very first bubble to start the game
		 */
		spawnBubble();
	}
	
	/** Code to spawn a new bubble with a random X position and at the top of the screen
	 */
	private void spawnBubble(){
		
	//Xpos = the x-position of the bubble, always assigned to a specific column
		int xPos = MathUtils.random(0,(int)(width)-cols);
		
	//Xpos is always assigned to a specific column so they dont drop from from random locations
		for(int i = 0; i<cols; i++){
			if(i*sideLength <= xPos && xPos < (i+1)*sideLength){
				xPos = i*sideLength;
			}
		}
		
	//a sprite of a bubble, with the above x-position is added to the array created earlier
		Sprite bubble = new Sprite(Assets.bubbleAtlas.findRegion("bubble0"));
		bubble.setPosition(xPos, height);
		bubble.setSize(sideLength, sideLength);
		bubbles.add(bubble);
		
	// this makes it so that if the highest bubble on the screen is 24, 49, 74, 99 ... etc. the next bubble will be a new color
		if(bubblesPopped + bubbles.lastIndexOf(bubble, true) - colorInterval == 24){
			colorInterval = bubblesPopped + bubbles.lastIndexOf(bubble, true) + 1;
			bubbleColor++;
			if(bubbleColor > 5) bubbleColor=0;
			Assets.setBubbleColor(bubbleColor);
		}
	}
	
	@Override
	public void update(GameScreen screen, float deltaTime) {
		
	// used to determine animation frames, set to 0 when a bubble is popped, bubble is flagged as popped, then the animation plays
	// 		using this frameTime
		stateTime += deltaTime;
		
	// new bubble spawns as soon as the one ahead of it moves one sidelength down the screen
		yPos += speed*deltaTime;
		if(yPos - sideLength >= 0){
			yPos = 0;
			spawnBubble();
		}
		
	/** This makes it so you can only pop the lowest bubble on the screen or else you activate the failure flag
	 * 
	 * 	If the lead bubble is tapped AND the tap was within reasonable distance of the red line
	 * 		-> start pop animation at its location, remove bubble from array, increment score
	 * 
	 *  Else the lead bubble was missed 
	 *  	-> set game over flag true
	 */
		leadBubble = bubbles.first();
        if (Gdx.input.justTouched()){
        	if(Gdx.input.getX()>=leadBubble.getX() && Gdx.input.getX() <= leadBubble.getX()+leadBubble.getWidth()
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= leadBubble.getY() 
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= leadBubble.getY()+leadBubble.getHeight()
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= sideLength)
        	{
	    		stateTime=0;
	    		Assets.previousBubble.setPosition(leadBubble.getX(), leadBubble.getY());
	    		popStarted=true;
	    		removeBubble = true;
	    		Assets.playSound(Assets.bubbleSound);
	    		bubblesPopped++;
        	}
        	else{
        		missedBubble = true;
        	}
        }
        
    // This is here to update the array of bubbles
		Iterator<Sprite> iter = bubbles.iterator();
        while (iter.hasNext()) {
        	
        // moves each bubble in the array down by a distance proportional to the speed
        	Sprite bubble = iter.next();
            bubble.setY(bubble.getY() - speed * deltaTime);
            
        // if the bubble was tapped, removeBubble was set to true, therefore remove it from the array and reset the flag to false
            if(removeBubble)
            {
    	       	iter.remove();
    	       	removeBubble = false;
            }
            
        // If the bubble makes it off the bottom of the screen you missed it, therefore you fail
            if (bubble.getY() < -sideLength){
                iter.remove();
                missedBubble = true;
            }
        }
        
    // fail the game if a bubble has been missed, dispose the items associated with this play-through to free memory
        if (missedBubble){
        	Assets.playSound(Assets.failSound);
        	dispose();
        	screen.setState(new GameStateGetName(screen, (int)bubblesPopped, "SPEED BUBBLES", "SUCCESS!"));
        }
        
    // increments the speed at a constant proportion of the screen height, so the bubbles don't wind up moving at different speeds
    //		for different phone sizes.
        speed += (height/25) * deltaTime;
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	// draw background
		batch.begin();
		background.draw(batch);
		batch.draw(arcadeLine, 0, 2*sideLength/5, width, 3*sideLength/5);
		batch.end();
		
	// draw foreground
		batch.begin();
		gameFont.draw(batch, ""+bubblesPopped, (Gdx.graphics.getWidth() - gameFont.getBounds(""+bubblesPopped).width)/2,
				gameFont.getBounds(""+bubblesPopped).height*3/2);
		for (Sprite bubble : bubbles){
			bubble.draw(batch);
		}
		if(popStarted) drawAnimation();
		batch.end();
	}
	
	/** Draw the popping bubble animation, and draw it where the previous bubble was located
	 */
	public void drawAnimation(){
		Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }
	
	public void dispose(){
		batch.dispose();
		backgroundTexture.dispose();
		arcadeLine.dispose();
		gameFont.dispose();
	}

}
