package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Game Mode class for Pacer mode
 * 	This class uses the exact same methods and concepts as "GameModeFiftyBubble"
 * 
 * @author Dean
 *
 */
public class GameModePacer implements GameMode{
	
	private boolean popStarted, gameStarted;
    private float time;
    private float stateTime;
    private int bubbles, tracker, level, newColor;
    
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
        gameFont.setScale(1.5f);
    	gameFont.setColor(0, 0, 0, 1);
    	
    	popStarted = false;
    	gameStarted = false;
    	
    	time = 10;
    	stateTime = 0;
    	bubbles = 0;
    	tracker = 0;
    	level = 0;
    	
    	Assets.setBubbleColor(Settings.favoriteColor);
    	newColor = Settings.favoriteColor;
    }
	
	@Override
    public void update(GameScreen screen, float deltaTime) {
		stateTime += deltaTime;
		
		if (gameStarted){
        time -= deltaTime;
		}
        
    	 
    	if(tracker==24){
    		newColor++;
    		if (newColor>5) newColor=0;
    		Assets.setBubbleColor(newColor);
    	}
    	 
    	 if (tracker >= 25){
    		 Assets.setBubbleColor(Settings.favoriteColor);
    		 tracker = 0;
    		 level++;
    		 
    		 if(level>5){
    			 level = 5;
    		 }
    		 time += 9 - (float)level *.5f;    		 
    	 }
    	 
    	 if (time <= 0){
    		 Assets.playSound(Assets.failSound);
    		 Assets.setBubbleColor(Settings.favoriteColor);
    		 dispose();
    		 screen.setState(new GameStateGetName(screen, (int)bubbles, "PACER", "SUCCESS!")); 
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
             bubbles++;
             tracker++;
             }
         else if (Gdx.input.justTouched()){
         	Assets.playSound(Assets.failSound);
         	Assets.setBubbleColor(Settings.favoriteColor);
         	dispose();
         	screen.setState(new GameStateGetName(screen, (int)bubbles, "PACER", "SUCCESS!"));
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
        Assets.face.draw(batch);
        gameFont.draw(batch, "BUBBLES: " + bubbles, (Gdx.graphics.getWidth() - gameFont.getBounds("BUBBLES: "+bubbles).width)/2,
        		Gdx.graphics.getHeight() - 2*gameFont.getBounds("BUBBLES:").height);
        gameFont.draw(batch, "TIME: " + String.format("%.3f", time), 
        		(Gdx.graphics.getWidth() - gameFont.getBounds("TIME: "+String.format("%.3f", time)).width)/2, 
        		 Gdx.graphics.getHeight() - 7*gameFont.getBounds("TIME: 10.000").height/2);
        batch.end();
	}
	
	public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
    }
	
	public void dispose() {
		gameFont.dispose();
		batch.dispose();
		backgroundTexture.dispose();
	}
	

}
