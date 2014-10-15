package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Game Mode class for Rapid 25 Mode (used to be called 50 bubble)
 * 
 * @author Dean
 *
 */
public class GameModeFiftyBubble implements GameMode{
	
	private boolean popStarted, gameStarted, changeColor;
    private float time;
    private float stateTime;
    private int bubbles;
    
    private BitmapFont gameFont;
    
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite background;
    private float width, height;
    
    public GameModeFiftyBubble (){
    	/** Initialize all of the generic images, text, and sprite batch
		 */
    	batch = new SpriteBatch();
    	backgroundTexture = new Texture(Gdx.files.internal("inGameBackground.png"));
    	background = new Sprite(backgroundTexture);
    	gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
        gameFont.setScale(1.5f);
    	gameFont.setColor(0, 0, 0, 1);
    	
    	/** Create flags
    	 * 
    	 * popStarted = used to determine when to start drawing a popping animation
    	 * gameStarted = activated when the first bubble is popped to start incrementing the score clock
    	 * changeColor = activated when the second to last bubble is popped, used to change bubble color indicating end of round
    	 */
    	popStarted = false;
    	gameStarted = false;
    	changeColor = false;
    	
    	/** Variable initialization
    	 * 
    	 * width = width of the screen or device
    	 * height = height of the screen or device
    	 * time = the score
    	 * stateTime = used to determine what frame to draw in the the bubble popping animation
    	 * bubbles = the remaining number of bubbles that need to be popped to finish the game
    	 */
    	width = Gdx.graphics.getWidth();
    	height = Gdx.graphics.getHeight();
    	time = 0;
    	stateTime = 0;
    	bubbles = 25;
    	
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
			background.setPosition(0,0);
		}
    	
    	/** Resets the color of the bubbles to the user's favorite color
    	 */
    	Assets.setBubbleColor(Settings.favoriteColor);
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		
		// used to determine animation frames, set to 0 when a bubble is popped, bubble is flagged as popped, then the animation plays
		// 		using this stateTime to determine when to cycle through frames
		stateTime += deltaTime;
		
		// starts the score timer when the first bubble is popped, this time is used to determine the player's score
		if (gameStarted){
			time += deltaTime;
		}
        
		/** This makes it so that the player can only pop the solid bubble
		 * 
		 * if the solid bubble is tapped (input is inside the bubble boundaries)
		 * 		->	reset the animation stateTime, set location for animation to take place, start the popping animations,
		 * 			start the game, change the location of the bubbles on the screen, decrease remaining bubbles by 1,
		 * 			activate the changeColor flag if there is only 1 more bubble
		 * 
		 * else if you tapped the screen and missed the bubble
		 * 		->	play the fail sound, reset the bubble color to the user's favorite color, dispose of objects associated with
		 * 			this play through, get a FAILED game-over screen
		 */
		if(Gdx.input.justTouched() && Gdx.input.getX()>=Assets.bubble.getX() 
				&& Gdx.input.getX() <= Assets.bubble.getX()+Assets.bubble.getWidth()
				&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= Assets.bubble.getY() 
                && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= Assets.bubble.getY()+Assets.bubble.getHeight()){
			stateTime=0;
            Assets.previousBubble.setPosition(Assets.bubble.getX(), Assets.bubble.getY());
            popStarted=true;
            gameStarted = true;
            Assets.relocateBubble();
            Assets.playSound(Assets.bubbleSound);
            bubbles--;
            if(bubbles==1) changeColor = true;
        }
        else if (Gdx.input.justTouched()){
            Assets.playSound(Assets.failSound);
            Assets.setBubbleColor(Settings.favoriteColor);
            dispose();
            screen.setState(new GameStateGetName(screen, (float)time, "RAPID 25", "FAILED!"));
        }
    	
		/** change the color of the bubbles to indicate you have reached the end of the game
		 */
    	if (changeColor) {
    		Assets.setBubbleColor(Settings.favoriteColor + 1);
    		changeColor = false;
    	}
    	
    	/** the user has successfully completed the game, 
    	 * 	dispose of objects associated with this play-through
    	 * get a SUCCESS game-over screen
    	 */
    	if (bubbles <= 0){
    		Assets.setBubbleColor(Settings.favoriteColor);
    		dispose();
    		screen.setState(new GameStateGetName(screen, (float)time, "RAPID 25", "SUCCESS!"));
    	}
    }
	
	public void draw(){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // draw background
    	batch.begin();
    	background.draw(batch);
    	batch.end();
    	
    	// draw foreground
    	batch.begin();
        if(popStarted) drawAnimation();
        if(bubbles>0){
        	Assets.bubble.draw(batch);
        	Assets.face.draw(batch);
        }
        if(bubbles>1) Assets.phantom.draw(batch);
        gameFont.draw(batch, "BUBBLES: " + bubbles, (Gdx.graphics.getWidth() - gameFont.getBounds("BUBBLES: " + bubbles).width)/2,
        		Gdx.graphics.getHeight() - 2*gameFont.getBounds("BUBBLES:").height);
        gameFont.draw(batch, "TIME: " + String.format("%.3f", time), 
        		(Gdx.graphics.getWidth() - gameFont.getBounds("TIME: 0.000").width)/2,   Gdx.graphics.getHeight() - 7 * gameFont.getBounds("TIME: 10.000").height/2);
        batch.end();
	}
	
	public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }
	
	public void dispose(){
		batch.dispose();
		gameFont.dispose();
		backgroundTexture.dispose();
	}

}