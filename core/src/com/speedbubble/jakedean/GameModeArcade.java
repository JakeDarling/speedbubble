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
	private Texture background, arcadeLine;
	private BitmapFont gameFont;
	private boolean popStarted, missedBubble;

	private Array<Sprite> bubbles;
	private Sprite topBubble;
	private int bubblesPopped, cols, sideLength, width, height;
	private float stateTime, speed, yPos;
	
	public GameModeArcade(){
		
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("inGameBackground.png"));
		arcadeLine = new Texture(Gdx.files.internal("arcadeLine.png"));
    	
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	
    	popStarted = false;
    	missedBubble = false;
    	
    	stateTime = 0;
    	bubblesPopped = 0;
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		bubbles = new Array<Sprite>();
		
		cols = 5;
		sideLength = width/cols;
		
		spawnBubble();
		
		speed = 200;
		yPos = 0;
		
	}
	
	private void spawnBubble(){
		int xPos = MathUtils.random(0,width-sideLength);
		
		for(int i = 0; i<cols; i++){
			if(i*sideLength <= xPos && xPos < (i+1)*sideLength){
				xPos = i*sideLength;
			}
		}
		
		Sprite bubble = new Sprite(Assets.bubbleAtlas.findRegion("bubble0"));
		bubble.setPosition(xPos, 0-sideLength);
		bubble.setSize(sideLength, sideLength);
		bubbles.add(bubble);
	}
	
	@Override
	public void update(GameScreen screen, float deltaTime) {
		stateTime += deltaTime;
		
		yPos += speed*deltaTime;
		if(yPos - sideLength >= 0){
			yPos = 0;
			spawnBubble();
		}
		
		topBubble = bubbles.first();
        
        if (Gdx.input.justTouched()){
        	if(Gdx.input.getX()>=topBubble.getX() && Gdx.input.getX() <= topBubble.getX()+topBubble.getWidth()
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= topBubble.getY() 
    			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= topBubble.getY()+topBubble.getHeight()
    			&& topBubble.getY() > Gdx.graphics.getHeight() - 3*sideLength/2)
        	{
	    		stateTime=0;
	    		Assets.previousBubble.setPosition(topBubble.getX(), topBubble.getY());
	    		popStarted=true;
	    		Assets.bubbleSound.play();
	    		bubblesPopped++;
        	}
        	else{
        		missedBubble = true;
        	}
        }
		
		Iterator<Sprite> iter = bubbles.iterator();
        while (iter.hasNext()) {
        	Sprite bubble = iter.next();
            bubble.setY(bubble.getY() + speed * deltaTime);
            if (Gdx.input.justTouched()){
            	if(Gdx.input.getX()>=bubble.getX() && Gdx.input.getX() <= bubble.getX()+bubble.getWidth()
        			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= bubble.getY() 
        			&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= bubble.getY()+bubble.getHeight()
        			&& Gdx.input.getY() < sideLength)
            	{
    	        	iter.remove();
            	}
            }
            if (bubble.getY() > height){
                iter.remove();
                missedBubble = true;
            }
        }
        
        if (missedBubble){
        	Assets.failSound.play();
        	screen.setState(new GameStateOverArcade(bubblesPopped));
        }

        speed += 5 * deltaTime;
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, Gdx.graphics.getWidth()/2 - 1024, Gdx.graphics.getHeight()/2 - 512);
		batch.draw(arcadeLine, 0, height - 3*sideLength/4, width, 3*sideLength/4);
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
