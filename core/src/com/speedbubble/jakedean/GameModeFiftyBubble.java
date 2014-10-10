package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameModeFiftyBubble implements GameMode{
	
	private boolean popStarted, gameStarted;
    private float time;
    private float stateTime;
    private int bubbles;
    
    private BitmapFont gameFont;
    
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite background;
    private float width, height;
    
    public GameModeFiftyBubble (){
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
        gameFont.setScale(1.5f);
    	gameFont.setColor(0, 0, 0, 1);
    	
    	popStarted = false;
    	gameStarted = false;
    	time = 0;
    	stateTime = 0;
    	bubbles = 25;
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		stateTime += deltaTime;
		
		if (gameStarted){
			time += deltaTime;
		}
        
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
        }
        else if (Gdx.input.justTouched()){
            Assets.playSound(Assets.failSound);
            Assets.setBubbleColor(Settings.favoriteColor);
            bubbles = 25;
            dispose();
            screen.setState(new GameStateFailFB(screen, time));
        }
    	 
    	if (bubbles == 1) Assets.setBubbleColor(Settings.favoriteColor + 1);
    	if (bubbles <= 0){
    		Assets.setBubbleColor(Settings.favoriteColor);
    		dispose();
    		screen.setState(new GameStateGetName(screen, time)); 
    	}
    }
	
	public void draw(){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
         
    	batch.begin();
    	background.draw(batch);
        if(popStarted) drawAnimation();
        if(bubbles>0) Assets.bubble.draw(batch);
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