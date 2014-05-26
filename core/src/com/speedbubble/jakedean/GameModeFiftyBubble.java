package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameModeFiftyBubble implements GameMode{
	
	private boolean popStarted, gameStarted;
    private float time;
    private float stateTime;
    private int bubbles;
    
    private BitmapFont gameFont;
    
    private SpriteBatch batch;
    private Texture background;
    
    public GameModeFiftyBubble (){
    	batch = new SpriteBatch();
    	background = new Texture(Gdx.files.internal("inGameBackground.png"));
    	
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	
    	popStarted = false;
    	gameStarted = false;
    	time = 0;
    	stateTime = 0;
    	bubbles = 50;
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		if (gameStarted){
		stateTime += deltaTime;
        time += deltaTime;
		}
        
    	 if (Gdx.input.justTouched()){
             if(Gdx.input.getX()>=Assets.bubble.getX() && Gdx.input.getX() <= Assets.bubble.getX()+Assets.bubble.getWidth()
                     && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= Assets.bubble.getY() && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= Assets.bubble.getY()+Assets.bubble.getHeight()){
                 stateTime=0;
                 Assets.previousBubble.setPosition(Assets.bubble.getX(), Assets.bubble.getY());
                 popStarted=true;
                 gameStarted = true;
                 Assets.relocateBubble();
                 Assets.bubbleSound.play();
                 bubbles--;
             }
             else{ screen.setState(new GameStateFailFB(time)); }
         }
    	 
    	 if (bubbles <= 0){
    		 dispose();
    		 screen.setState(new GameStateOverFB(time)); 
    	 }
    }
	
	public void draw(){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
         
    	batch.begin();
    	batch.draw(background, Gdx.graphics.getWidth()/2 - 1024, Gdx.graphics.getHeight()/2 - 512);
        if(popStarted) drawAnimation();
        Assets.bubble.draw(batch);
        gameFont.draw(batch, "BUBBLES REMAINING: " + bubbles, (Gdx.graphics.getWidth() - gameFont.getBounds("BUBBLES REMAINING: 50").width)/2,
        		Gdx.graphics.getHeight());
        gameFont.draw(batch, "TIME: " + String.format("%.3f", time), 
        		(Gdx.graphics.getWidth() - gameFont.getBounds("TIME: 10.000").width)/2,   Gdx.graphics.getHeight() - 3 * gameFont.getBounds("TIME: 10.000").height/2);
        batch.end();
	}
	
	public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }
	
	public void dispose(){
		
	}

}