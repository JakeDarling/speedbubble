package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameModeTimed implements GameMode {
	
	private boolean popStarted, gameStarted;
    private float timeLeft;
    private float stateTime;
    private int score;
    
    private BitmapFont gameFont;
    
    private SpriteBatch batch;
    private Texture background;
    
    private String name;
    
    public GameModeTimed (){
    	batch = new SpriteBatch();
    	background = new Texture(Gdx.files.internal("inGameBackground.png"));
    	
    	gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	
    	popStarted = false;
    	gameStarted = false;
    	timeLeft = 15F;
    	stateTime = 0;
    	score = 0;
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		if (gameStarted){
		stateTime += deltaTime;
        timeLeft -= deltaTime;
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
                 score++;
             }
             else{
            	 Assets.playSound(Assets.failSound);
            	 screen.setState(new GameStateFailTimed(score));
             }
         }
    	 
    	 if (timeLeft < 0){
    		 screen.setState(new GameStateOverTimed(score)); 
    	 }
    }
	
	public void draw(){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
         
    	batch.begin();
    	batch.draw(background, Gdx.graphics.getWidth()/2 - 1024, Gdx.graphics.getHeight()/2 - 512);
        if(popStarted) drawAnimation();
        Assets.bubble.draw(batch);
        gameFont.draw(batch, "BUBBLES POPPED: " + score, (Gdx.graphics.getWidth() - gameFont.getBounds("BUBBLES POPPED: 10").width)/2,
        		Gdx.graphics.getHeight() - 3 * gameFont.getBounds("BUBBLES POPPED: 10").height/2);
        gameFont.draw(batch, "TIME REMAINING: " + String.format("%.2f", timeLeft), 
        		(Gdx.graphics.getWidth() - gameFont.getBounds("TIME REMAINING: 15.00").width)/2,   Gdx.graphics.getHeight());
        batch.end();
	}
	
	public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }

}
