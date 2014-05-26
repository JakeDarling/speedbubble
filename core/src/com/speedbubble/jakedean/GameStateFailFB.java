package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateFailFB implements GameState {
	
    private float score;
    private SpriteBatch batch;
    
    private BitmapFont gameFont, gameFont2;

    public GameStateFailFB(float score) {
        this.score = score;
        batch = new SpriteBatch();
        
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	
    	gameFont2 = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont2.setColor(1, 1, 1, 1);
    	gameFont2.setScale(.75f);
    }

    @Override
    public void draw(GameScreen screen, float deltaTime) {
    	
    	Gdx.gl.glClearColor(1,0,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	batch.begin();
    	gameFont.setColor(1, 1, 1, 1);
    	gameFont.draw(batch, "FIFTY BUBBLE", (Gdx.graphics.getWidth() - gameFont.getBounds("FIFTY BUBBLE").width)/2,
    	        Gdx.graphics.getHeight() - 2*gameFont.getBounds("FIFTY BUBBLE").height);
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.draw(batch, "FAIL", (Gdx.graphics.getWidth() - gameFont.getBounds("FAIL").width)/2,
    	        Gdx.graphics.getHeight()/2 + (float)2.5*gameFont.getBounds("FAIL").height);
//    	gameFont.draw(batch, "BEST: " + String.format("%.3f", score), (Gdx.graphics.getWidth() - gameFont.getBounds("BEST:    ").width)/2,
//    	        Gdx.graphics.getHeight()/2 + gameFont.getBounds("BEST:    ").height/2);
    	gameFont2.draw(batch, "MAIN MENU", (Gdx.graphics.getWidth() - gameFont2.getBounds("MAIN MENU").width)/4,
    	        (float)2.5*gameFont2.getBounds("MAIN MENU").height);
    	gameFont2.draw(batch, "RETRY", (Gdx.graphics.getWidth() - gameFont2.getBounds("RETRY").width)*3/4,
    	        (float)2.5*gameFont2.getBounds("RETRY").height);
    	batch.end();
    }

	@Override
	public void update(GameScreen screen, float deltaTime) {
		if (Gdx.input.justTouched() && Gdx.input.getX() > (Gdx.graphics.getWidth() - gameFont2.getBounds("MAIN MENU").width)/4 - 5 &&
				Gdx.input.getX() < (Gdx.graphics.getWidth() - gameFont2.getBounds("MAIN MENU").width)/4 + gameFont2.getBounds("MAIN MENU").width + 5 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) > (float)2.5*gameFont2.getBounds("MAIN MENU").height - gameFont2.getBounds("MAIN MENU").height - 5 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) < (float)2.5*gameFont2.getBounds("MAIN MENU").height + 5){
    		screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
    	}
		if (Gdx.input.justTouched() && Gdx.input.getX() > (Gdx.graphics.getWidth() - gameFont2.getBounds("RETRY").width)*3/4 -5 &&
				Gdx.input.getX() < (Gdx.graphics.getWidth() - gameFont2.getBounds("RETRY").width)*3/4 + gameFont2.getBounds("RETRY").width +5 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) > (float)2.5*gameFont2.getBounds("RETRY").height - gameFont2.getBounds("RETRY").height -5 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) < (float)2.5*gameFont2.getBounds("RETRY").height +5){
			screen.reset();
    	}
	}

}