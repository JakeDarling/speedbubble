package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameModePacer implements GameMode{
	
	private boolean popStarted, gameStarted;
    private float time;
    private float stateTime;
    private int bubbles, tracker, level;
    
    private BitmapFont gameFont;
    
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite background;
    private float width, height;
    
    public GameModePacer (){
    	batch = new SpriteBatch();
    	backgroundTexture = new Texture(Gdx.files.internal("inGameBackground.png"));
    	background = new Sprite(backgroundTexture);
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
			background.setPosition(0,0);
		}
    	
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	
    	popStarted = false;
    	gameStarted = false;
    	time = 10;
    	stateTime = 0;
    	bubbles = 0;
    	tracker = 0;
    	level = 0;
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		if (gameStarted){
		stateTime += deltaTime;
        time -= deltaTime;
		}
        
    	 if (Gdx.input.justTouched()){
             if(Gdx.input.getX()>=Assets.bubble.getX() && Gdx.input.getX() <= Assets.bubble.getX()+Assets.bubble.getWidth()
                     && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= Assets.bubble.getY() && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= Assets.bubble.getY()+Assets.bubble.getHeight()){
                 stateTime=0;
                 Assets.previousBubble.setPosition(Assets.bubble.getX(), Assets.bubble.getY());
                 popStarted=true;
                 gameStarted = true;
                 Assets.relocateBubble();
                 Assets.playSound(Assets.bubbleSound);
                 bubbles++;
                 tracker++;
             }
             else{
            	 Assets.playSound(Assets.failSound);
            	 screen.setState(new GameStateGetName(screen, bubbles));
             }
         }
    	 
    	 if (tracker >= 25){
    		 tracker = 0;
    		 level++;
    		 if(level>5){
    			 level = 5;
    		 }
    		 time = 10 - level*.5f;    		 
    	 }
    	 
    	 if (time <= 0){
    		 Assets.playSound(Assets.failSound);
    		 screen.setState(new GameStateGetName(screen, bubbles)); 
    	 }
    }
	
	public void draw(){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
         
    	batch.begin();
    	background.draw(batch);
        if(popStarted) drawAnimation();
        Assets.phantom.draw(batch);
        Assets.bubble.draw(batch);
        gameFont.draw(batch, "BUBBLES POPPED: " + bubbles, (Gdx.graphics.getWidth() - gameFont.getBounds("BUBBLES REMAINING: 50").width)/2,
        		Gdx.graphics.getHeight());
        gameFont.draw(batch, "TIME: " + String.format("%.3f", time), 
        		(Gdx.graphics.getWidth() - gameFont.getBounds("TIME: 10.000").width)/2,   Gdx.graphics.getHeight() - 3 * gameFont.getBounds("TIME: 10.000").height/2);
        batch.end();
	}
	
	public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }
	

}
