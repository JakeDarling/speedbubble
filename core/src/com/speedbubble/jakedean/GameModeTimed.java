package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameModeTimed implements GameMode {
	
	private boolean popStarted;
    private float timeLeft;
    private float stateTime;
    private int score;
    
    private SpriteBatch batch;
    private Texture background;
    
    public GameModeTimed (){
    	batch = new SpriteBatch();
    	background = new Texture(Gdx.files.internal("inGameBackground.png"));
    	
    	popStarted = false;
    	timeLeft = 15F;
    	stateTime = 0;
    	score = 0;
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		stateTime += deltaTime;
        timeLeft -= deltaTime;
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        
    	 if (Gdx.input.justTouched()){
             if(Gdx.input.getX()>=Assets.bubble.getX() && Gdx.input.getX() <= Assets.bubble.getX()+Assets.bubble.getWidth()
                     && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= Assets.bubble.getY() && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= Assets.bubble.getY()+Assets.bubble.getHeight()){
                 stateTime=0;
                 Assets.previousBubble.setPosition(Assets.bubble.getX(), Assets.bubble.getY());
                 popStarted=true;
                 Assets.relocateBubble();
                 Assets.bubbleSound.play();
                 score++;
             }
             else{ screen.setState(new GameStateFail(score)); }
         }
    	 
    	 if (timeLeft < 0){
    		 dispose();
    		 screen.setState(new GameStateOver(score)); 
    	 }
    }
	
	public void draw(){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
         
    	batch.begin();
    	batch.draw(background, Gdx.graphics.getWidth()/2 - 1024, Gdx.graphics.getHeight()/2 - 512);
        if(popStarted) drawAnimation();
        Assets.bubble.draw(batch);
        Assets.bubbleFontBig.draw(batch, "BUBBLES POPPED: " + score, 0,
        		Assets.bubbleFontBig.getBounds("BUBBLES POPPED:  ").height + 10);
        Assets.bubbleFontBig.draw(batch, "TIME REMAINING: " + String.format("%.2f", timeLeft), 
        		(Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("TIME REMAINING:  ").width)/2,   Gdx.graphics.getHeight());
        batch.end();
	}
	
	public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }
	
	public void dispose(){
		
	}
}
