package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateFailTimed implements GameState {
	
    private int score;
    private SpriteBatch batch;
    
    private BitmapFont gameFont, gameFont2;

    public GameStateFailTimed(int score) {
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
    	gameFont.draw(batch, "TIMED", (Gdx.graphics.getWidth() - gameFont.getBounds("TIMED").width)/2,
    	        Gdx.graphics.getHeight() - 2*gameFont.getBounds("TIMED").height);
    	gameFont.setColor(0, 0, 0, 1);
        gameFont.draw(batch, "FAIL", (Gdx.graphics.getWidth() - gameFont.getBounds("FAIL").width)/2,
                Gdx.graphics.getHeight()/2 + (float)2.5*gameFont.getBounds("FAIL").height);
        gameFont.draw(batch, "FINAL SCORE: " + score, (Gdx.graphics.getWidth() - gameFont.getBounds("FINAL SCORE: 0").width)/2,
                Gdx.graphics.getHeight()/2 + gameFont.getBounds("FINAL SCORE: 0").height/2);
        gameFont.setColor(1, 1, 1, 1);
    	gameFont.draw(batch, "MAIN MENU", (Gdx.graphics.getWidth() - gameFont.getBounds("MAIN MENU").width)/4,
    	        (float)2.5*gameFont.getBounds("MAIN MENU").height);
    	gameFont.draw(batch, "RETRY", (Gdx.graphics.getWidth() - gameFont.getBounds("RETRY").width)*3/4,
    	        (float)2.5*gameFont.getBounds("RETRY").height);
	    batch.end();
    }

	@Override
	public void update(GameScreen screen, float deltaTime) {
		if (Gdx.input.justTouched() && Gdx.input.getX() > (Gdx.graphics.getWidth() - gameFont.getBounds("MAIN MENU").width)/4 - 15 &&
				Gdx.input.getX() < (Gdx.graphics.getWidth() - gameFont.getBounds("MAIN MENU").width)/4 + gameFont.getBounds("MAIN MENU").width + 15 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) > (float)2.5*gameFont.getBounds("MAIN MENU").height - gameFont.getBounds("MAIN MENU").height - 15 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) < (float)2.5*gameFont.getBounds("MAIN MENU").height + 15){
    		screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
    	}
		if (Gdx.input.justTouched() && Gdx.input.getX() > (Gdx.graphics.getWidth() - gameFont.getBounds("RETRY").width)*3/4 - 15 &&
				Gdx.input.getX() < (Gdx.graphics.getWidth() - gameFont.getBounds("RETRY").width)*3/4 + gameFont.getBounds("RETRY").width + 15 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) > (float)2.5*gameFont.getBounds("RETRY").height - gameFont.getBounds("RETRY").height - 15 &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) < (float)2.5*gameFont.getBounds("RETRY").height + 15){
			screen.reset();
    	}
	}

}
