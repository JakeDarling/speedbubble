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
	private boolean popStarted, missedBubble;

	private Array<Sprite> bubbles;
	private Sprite topBubble, background;
	private int bubblesPopped, cols, sideLength, bubbleColor, colorInterval;
	private float stateTime, speed, yPos, width, height;
	
	public GameModeArcade(){
		
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("inGameBackground.png"));
		background = new Sprite(backgroundTexture);
		arcadeLine = new Texture(Gdx.files.internal("arcadeLine.png"));
    	
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
        gameFont.setScale(1.5f);
    	gameFont.setColor(1, 1, 1, 1);
    	
    	popStarted = false;
    	missedBubble = false;
    	
    	stateTime = 0;
    	bubblesPopped = 0;
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
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
		
		bubbles = new Array<Sprite>();
		
		cols = 5;
		sideLength = (int)(width/cols);
		
		spawnBubble();
		
		speed = Gdx.graphics.getHeight()/2;
		yPos = 0;
		
		bubbleColor=0;
		colorInterval=0;
		
	}
	
	private void spawnBubble(){
		int xPos = MathUtils.random(0,(int)(width)-cols);
		
		for(int i = 0; i<cols; i++){
			if(i*sideLength <= xPos && xPos < (i+1)*sideLength){
				xPos = i*sideLength;
			}
		}
		
		Sprite bubble = new Sprite(Assets.bubbleAtlas.findRegion("bubble0"));
		bubble.setPosition(xPos, height);
		bubble.setSize(sideLength, sideLength);
		bubbles.add(bubble);
		
		if(bubblesPopped + bubbles.lastIndexOf(bubble, true) - colorInterval == 24){
			colorInterval = bubblesPopped + bubbles.lastIndexOf(bubble, true) + 1;
			bubbleColor++;
			if(bubbleColor > 5) bubbleColor=0;
			Assets.setBubbleColor(bubbleColor);
		}
	}
	
	@Override
	public void update(GameScreen screen, float deltaTime) {
		
		stateTime += deltaTime;
		
		yPos += speed*deltaTime;
		if(yPos - sideLength >= 0){
			yPos = 0;
			spawnBubble();
		}
		
		/**
		 * this makes it so you can only pop the upper most bubble on the screen or else you fail
		 */
		topBubble = bubbles.first();
        if (Gdx.input.justTouched()){
        	if(Gdx.input.getX()>=topBubble.getX() && Gdx.input.getX() <= topBubble.getX()+topBubble.getWidth()
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= topBubble.getY() 
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= topBubble.getY()+topBubble.getHeight()
    			&& topBubble.getY() < 3*sideLength/2)
        	{
	    		stateTime=0;
	    		Assets.previousBubble.setPosition(topBubble.getX(), topBubble.getY());
	    		popStarted=true;
	    		Assets.playSound(Assets.bubbleSound);
	    		bubblesPopped++;
        	}
        	else{
        		missedBubble = true;
        	}
        }
        
		/**
		 * This is here solely to remove any "popped" bubble from the screen
		 */
		Iterator<Sprite> iter = bubbles.iterator();
        while (iter.hasNext()) {
        	Sprite bubble = iter.next();
            bubble.setY(bubble.getY() - speed * deltaTime);
            if (Gdx.input.justTouched()){
            	if(Gdx.input.getX()>=bubble.getX() && Gdx.input.getX() <= bubble.getX()+bubble.getWidth()
        			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= bubble.getY() 
        			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= bubble.getY()+bubble.getHeight()
        			&& Gdx.input.getY() > height - sideLength)
            	{
    	        	iter.remove();
            	}
            }
        /**
         * If the bubble makes it off the bottom of the screen you missed it, therefore you fail
         */
            if (bubble.getY() < -sideLength){
                iter.remove();
                missedBubble = true;
            }
        }
        
        /**
         * only fail the game if a bubble has been missed
         */
        if (missedBubble){
        	Assets.playSound(Assets.failSound);
        	Assets.setBubbleColor(0);
        	screen.setState(new GameStateGetName(screen, bubblesPopped));
        }

        speed += (Gdx.graphics.getHeight()/100) * deltaTime;
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		background.draw(batch);
		batch.draw(arcadeLine, 0, 0, width, 3*sideLength/5);
		gameFont.draw(batch, ""+bubblesPopped, (Gdx.graphics.getWidth() - gameFont.getBounds(""+bubblesPopped).width)/2,
				gameFont.getBounds(""+bubblesPopped).height*3/2);
		for (Sprite bubble : bubbles){
			bubble.draw(batch);
		}
		if(popStarted) drawAnimation();
		batch.end();
	}
	
	public void drawAnimation(){
		Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }

}
