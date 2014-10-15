package com.speedbubble.jakedean;


import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/** Game Mode Class for Arcade mode
 * 
 * @author Dean
 *
 */
public class GameModeArcade implements GameMode{
	
	private SpriteBatch batch;
	private Texture backgroundTexture, arcadeLine;
	private BitmapFont gameFont;
	private boolean popStarted, missedBubble, removeBubble;

	private Array<Bubble> bubbles;
	private Bubble leadBubble;
	private Sprite background;
	private int bubblesPopped, cols, sideLength, bubbleColor, colorInterval;
	private float stateTime, speed, yPos, width, height, postGameTimer;
	
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
		postGameTimer = 0;
		
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
		 * bubbles = array of bubble objects
		 * cols = used to determine how many columns of bubbles
		 */
		bubbles = new Array<Bubble>();
		cols = 5;
		
		/** Variables used to determine when to spawn the next bubble (a new bubble spawns at the top of the screen
		 *		as soon as the bubble before it makes it one side-length down the screen)
		 */
		speed = height/2;
		yPos = 0;
		sideLength = (int)(width/cols);
		
		/** Variables used to determine what color to make the bubbles as they are falling.
		 * 		the bubble color will alternate every 25th bubble
		 * 		resets the color at the beginning of the game
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
		
		Bubble b = new Bubble();
		b.spawn(cols);
		bubbles.add(b);
		
	// this makes it so that if the highest bubble on the screen is 25, 50, 75, 100 ... etc. the next bubble will be a new color
		if(bubblesPopped + bubbles.lastIndexOf(b, true) - colorInterval == 24){
			colorInterval = bubblesPopped + bubbles.lastIndexOf(b, true) + 1;
			bubbleColor++;
			if(bubbleColor > 5) bubbleColor=0;
			Assets.setBubbleColor(bubbleColor);
		}
	}
	
	@Override
	public void update(GameScreen screen, float deltaTime) {
		
		/**	fail the game if a bubble has been missed
		 * 	pause the game for 1 second
		 * 	dispose the items associated with this play-through to free memory
		 * 	set the game-state to game-over
		 */
        if (missedBubble){
        	postGameTimer += deltaTime;
        	if (postGameTimer >=1){
        		Assets.playSound(Assets.failSound);
        		dispose();
        		screen.setState(new GameStateGetName(screen, (int)bubblesPopped, "SPEED BUBBLES", "SUCCESS!"));
        	}
        }
        
        // if the user has not missed a bubble, continue updating the game normally
	    else{
			
		// used to determine animation frames, set to 0 when a bubble is popped, bubble is flagged as popped, then the animation plays
		// 		using this stateTime to determine when to cycle through frames
			stateTime += deltaTime;
			
		// new bubble spawns as soon as the one ahead of it moves one sidelength down the screen
			yPos += speed*deltaTime;
			if(yPos - sideLength >= 0){
				yPos = 0;
				spawnBubble();
			}
			
		/** This makes it so you can only pop the lowest bubble on the screen or else you activate the failure flag
		 * 
		 * 	If the lead bubble is tapped, (input is located inside the bubble boundaries)
		 * 		-> start pop animation at its location, remove bubble from array, increment score
		 * 
		 *  Else (the screen was tapped, but the lead bubble was missed)
		 *  	-> set game over flag true
		 */
			leadBubble = bubbles.first();
	        if (Gdx.input.justTouched()){
	        	if(Gdx.input.getX()>=leadBubble.xPosition && Gdx.input.getX() <= leadBubble.xPosition+leadBubble.sideLength
	    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= leadBubble.yPosition 
	    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= leadBubble.yPosition+leadBubble.sideLength)
	        	{
		    		stateTime=0;
		    		Assets.previousBubble.setPosition(leadBubble.xPosition, leadBubble.yPosition);
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
			Iterator<Bubble> iter = bubbles.iterator();
	        while (iter.hasNext()) {
	        	
	        // moves each bubble in the array down by a distance proportional to the speed
	        // moves each bubble side to side in a sin wave pattern according to its amplitude, frequency, and waveBegin properties
	        	Bubble bubble = iter.next();
	        	bubble.waveBegin += deltaTime;
	            bubble.yPosition = bubble.yPosition - speed * deltaTime;
	            bubble.xPosition = bubble.originalXPosition + bubble.amplitude*(float)Math.sin(bubble.frequency*bubble.waveBegin);
	            bubble.updatePosition();
	            
	        // if the LEAD bubble was tapped, removeBubble was set to true, therefore remove it from the array and reset the flag to false
	            if(removeBubble)
	            {
	    	       	iter.remove();
	    	       	removeBubble = false;
	            }
	            
	        // If the bubble makes it off the bottom of the screen, the user missed it, therefore activate the failure flag
	            if (bubble.yPosition < -sideLength){
	                iter.remove();
	                missedBubble = true;
	            }
	        }
	        
	        /**increments the speed at a constant proportion of the screen height, so the bubbles don't wind up moving at different speeds
	         * for different phone sizes. Sprite positions are determined by pixels. 
	         * 
	         * Think this: 2 phones, one phone has 1000x1000 resolution and a second has 2000x2000 resolution, 
	         * if the object is moving at 1000 pixels/second
	         * it will be moving faster on the first phone (clears screen in 1 second) than the second (clears screen in 2 seconds)
	         */
	        speed += (height/25) * deltaTime;
	    }
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	// draw background
		batch.begin();
		background.draw(batch);
		batch.end();
		
	// draw foreground
		batch.begin();
		gameFont.draw(batch, ""+bubblesPopped, (Gdx.graphics.getWidth() - gameFont.getBounds(""+bubblesPopped).width)/2,
				gameFont.getBounds(""+bubblesPopped).height*3/2);
		for (Bubble bubble : bubbles){
			bubble.background.draw(batch);
			bubble.face.draw(batch);
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
	
	/** destroys objects associated with this play through to free memory
	 */
	public void dispose(){
		batch.dispose();
		backgroundTexture.dispose();
		arcadeLine.dispose();
		gameFont.dispose();
	}

}
