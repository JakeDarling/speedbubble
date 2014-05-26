package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateStart implements GameState {
	
	private SpriteBatch batch;
	private Texture background;
	
	private BitmapFont gameFont;
	
	public GameStateStart(){
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("inGameBackground.png"));
		
		gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
	}
	
	public void update(GameScreen screen, float deltaTime){
		if (Gdx.input.justTouched()){
    		screen.setState(new GameStateRunning());
    	}
	}
	
	public void draw(GameScreen screen, float deltaTime){
		Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(background, Gdx.graphics.getWidth()/2 - 1024, Gdx.graphics.getHeight()/2 - 512);
        gameFont.draw(batch, "TAP ANYWHERE TO BEGIN", (Gdx.graphics.getWidth() - gameFont.getBounds("TAP ANYWHERE TO BEGIN").width) / 2,
        		Gdx.graphics.getHeight()/2 + gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
        batch.end();
	}
}
