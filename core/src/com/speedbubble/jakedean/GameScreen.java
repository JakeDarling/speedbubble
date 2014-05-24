package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.speedbubble.jakedean.MainMenuScreen.Mode;

public class GameScreen implements Screen {

    private SpeedBubble sb;
    private SpriteBatch batch;

    private GameMode mode;

    private boolean popStarted;
    private float timeLeft;
    private float stateTime;
    private int score;
    private Texture background;
    
    private enum GameState{
    	START, RUNNING, PAUSED, RESTART, RESUME, QUIT, GAME_OVER
    }
    
    GameState state;

    public GameScreen(SpeedBubble s, Mode m) {
        sb = s;
        stateTime = 0;
        timeLeft = 15f;
        popStarted = false;
        batch = new SpriteBatch();
        state = GameState.START;
        
        background = new Texture(Gdx.files.internal("mainMenuBackground.png"));
    }   
    
    @Override
    public void render(float delta) {
        update();
        draw();
    }
    
    
    
    private void update() {
    	
        switch(state){
		case START:
			updateStart();
			break;
		case RUNNING:
			updateRunning();
			break;
//		case PAUSED:
//			updatePaused();
//			break;
//		case RESTART:
//			updateRestart();
//			break;
//		case RESUME:
//			updateResume();
//			break;
//		case QUIT:
//			updateQuit();
//			break;
//		case GAME_OVER:
//			updateGameOver();
//			break;
		}
    }
    private void updateStart(){
    	if (Gdx.input.justTouched()){
    		state = GameState.RUNNING;
    	}
    }
    private void updateRunning(){
    	
        //mode.update(); TODO
    	
        stateTime += Gdx.graphics.getDeltaTime();
        timeLeft -= Gdx.graphics.getDeltaTime();
        
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
             else{ sb.setScreen(new FailureScreen(sb, score)); }
         }
    	 
    	 if (timeLeft < 0){
    		 dispose();
    		 sb.setScreen(new GameOverScreen(sb, score)); 
    	 }
    }
    
    
    
    
    private void draw(){
    	switch(state){
 		case START:
 			drawStart();
 			break;
 		case RUNNING:
 			drawRunning();
 			break;
// 		case PAUSED:
// 			drawPaused();
// 			break;
// 		case RESTART:
// 			drawRestart();
// 			break;
// 		case RESUME:
// 			drawResume();
// 			break;
// 		case QUIT:
// 			drawQuit();
// 			break;
// 		case GAME_OVER:
// 			drawGameOver();
// 			break;
 		}
    }
    private void drawStart(){
    	Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(background, Gdx.graphics.getWidth() - 1024, Gdx.graphics.getHeight() - 512);
        Assets.bubbleFontBig.draw(batch, "TAP ANYWHERE TO BEGIN", (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("TAP ANYWHERE TO BEGIN").width) / 2,
        		Gdx.graphics.getHeight()/2 + Assets.bubbleFontBig.getBounds("TAP ANYWHERE TO BEGIN").height);
        batch.end();
    }
    private void drawRunning(){
    	Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
    	
    	Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
         
    	batch.begin();
    	batch.draw(background, Gdx.graphics.getWidth() - 1024, Gdx.graphics.getHeight() - 512);
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
    
    
    
    @Override
    public void dispose() {

    }
    
    

    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void show() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
}
