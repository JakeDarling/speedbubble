package com.speedbubble.jakedean;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class SpeedBubble extends ApplicationAdapter {

	float timeLeft;
	int score;
	BitmapFont bubbleFontSmall, bubbleFontBig;
	
	Animation poppingBubble;
	TextureAtlas bubbleAtlas;
	TextureRegion[] bubbleFrames;
	TextureRegion currentBubble;
	
	Sound bubbleSound;
	
	Boolean popStarted = false, gameOver = false, failure = false;
	
	OrthographicCamera camera;
	
	Sprite bubble, previousBubble;
	
	SpriteBatch batch;
	
	float stateTime=0;
	
	@Override
	public void create () {
		
		bubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubble.mp3"));
		bubbleFontSmall = new BitmapFont(Gdx.files.internal("bubble.fnt"));
		bubbleFontSmall.setScale(.5f);
		bubbleFontBig = new BitmapFont(Gdx.files.internal("bubble.fnt"));
		
		bubbleAtlas = new TextureAtlas("bubbleAnimation.pack");
		bubbleFrames = new TextureRegion[5];
		
		for (int i = 0; i<5; i++){
			bubbleFrames[i] = bubbleAtlas.findRegion("bubble"+i);
		}
		
		poppingBubble = new Animation(.025f, bubbleFrames);
		
		bubble = new Sprite(bubbleAtlas.findRegion("bubble0"));
		relocateBubble();
		bubble.setSize(100, 100);
		previousBubble = new Sprite(bubbleAtlas.findRegion("bubble0"));
		previousBubble.setSize(100,100);
		
		batch = new SpriteBatch();
		
		timeLeft = 15;
		score = 0;
	}

	@Override
	public void render () {
		
		if(!gameOver && !failure){
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
	        stateTime += Gdx.graphics.getDeltaTime();
	        timeLeft -= Gdx.graphics.getDeltaTime();
	        
	        currentBubble = poppingBubble.getKeyFrame(stateTime, false);
	        
	        batch.begin();
	        update();
	        if(popStarted) drawAnimation();
	        bubble.draw(batch);
	        bubbleFontSmall.draw(batch, "BUBBLES POPPED: " + score, 0, 
	        		Gdx.graphics.getHeight());
	        bubbleFontSmall.draw(batch, "TIME REMAINING: " + String.format("%.2f", timeLeft), (Gdx.graphics.getWidth() - bubbleFontSmall.getBounds("TIME REMAINING:  ").width)/2, 
	        		Gdx.graphics.getHeight());
	        batch.end();
	        if (timeLeft < 0){ gameOver = true;}
        }
        else if(failure){
        	Gdx.gl.glClearColor(1,0,0,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        	batch.begin();
        	bubbleFontBig.draw(batch, "FAILURE", (Gdx.graphics.getWidth() - bubbleFontBig.getBounds("FAILURE").width)/2, 
	        		Gdx.graphics.getHeight()/2 + (float) 2.5*bubbleFontBig.getBounds("FAILURE").height);
        	bubbleFontBig.draw(batch, "FINAL SCORE: " + score, (Gdx.graphics.getWidth() - bubbleFontBig.getBounds("FINAL SCORE:  ").width)/2, 
	        		Gdx.graphics.getHeight()/2 + bubbleFontBig.getBounds("FINAL SCORE:  ").height/2);
        	batch.end();
        }
        else if (gameOver){
        	Gdx.gl.glClearColor(0,1,0,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        	batch.begin();
        	bubbleFontBig.draw(batch, "GAME OVER", (Gdx.graphics.getWidth() - bubbleFontBig.getBounds("GAME OVER").width)/2, 
	        		Gdx.graphics.getHeight()/2 + (float)2.5*bubbleFontBig.getBounds("GAME OVER").height);
        	bubbleFontBig.draw(batch, "FINAL SCORE: " + score, (Gdx.graphics.getWidth() - bubbleFontBig.getBounds("FINAL SCORE:  ").width)/2, 
	        		Gdx.graphics.getHeight()/2 + bubbleFontBig.getBounds("FINAL SCORE:  ").height/2);
        	batch.end();
        }
	}	
	
	public void relocateBubble(){
		bubble.setPosition(MathUtils.random(0,Gdx.graphics.getWidth()-bubble.getWidth()), MathUtils.random(0,
				Gdx.graphics.getHeight()-bubble.getHeight()));
	}
	
	public void update(){
		
		if (Gdx.input.justTouched()){
			if(Gdx.input.getX()>=bubble.getX() && Gdx.input.getX() <= bubble.getX()+bubble.getWidth()
				&& ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= bubble.getY() && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= bubble.getY()+bubble.getHeight()){
			stateTime=0;
			previousBubble.setPosition(bubble.getX(), bubble.getY());
			popStarted=true;
			relocateBubble();
			bubbleSound.play();
			score++;
			}
			else{failure = true;}
		}
	}
	
	public void drawAnimation(){
		currentBubble = poppingBubble.getKeyFrame(stateTime, false);
		batch.draw(currentBubble, previousBubble.getX(), previousBubble.getY(), previousBubble.getWidth(), previousBubble.getHeight());
	}
	
}
