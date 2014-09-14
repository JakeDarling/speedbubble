package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateFailFB implements GameState {
	
    private SpriteBatch batch;
    
    private Score highestScore;
    
    private BitmapFont gameFont;

    public GameStateFailFB(float score) {
        batch = new SpriteBatch();
        
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.setScale(Gdx.graphics.getHeight()/ (gameFont.getBounds("ARCADE").height * 13) );
    	
    	highestScore = HighScores.fetchHighScores(HighScores.FIFTY, false).first();
    }

    @Override
    public void draw(GameScreen screen, float deltaTime) {
    	
    	Gdx.gl.glClearColor(1,0,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	batch.begin();
    	gameFont.setColor(1, 1, 1, 1);
    	gameFont.draw(batch, "SPEED BUBBLE", (Gdx.graphics.getWidth() - gameFont.getBounds("SPEED BUBBLE").width)/2,
    	        Gdx.graphics.getHeight()/2 + 11*gameFont.getBounds("SPEED BUBBLE").height/2);
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.draw(batch, "FAIL", (Gdx.graphics.getWidth() - gameFont.getBounds("FAIL").width)/2,
    			Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds("FAIL").height/2);
    	gameFont.setColor(0,1,0,1);
    	gameFont.draw(batch, "HIGHEST SCORE: " + String.format("%.3f", highestScore.getScore()), (Gdx.graphics.getWidth() - gameFont.getBounds("HIGHEST SCORE: " + String.format("%.3f", highestScore.getScore())).width)/2,
    	        Gdx.graphics.getHeight()/2 - 1*gameFont.getBounds("HIGHEST SCORE: 00.00").height/2);
    	gameFont.draw(batch, "SET BY: " + highestScore.getName(), (Gdx.graphics.getWidth() - gameFont.getBounds("SET BY: " + highestScore.getName()).width)/2,
    			 Gdx.graphics.getHeight()/2 - 2*gameFont.getBounds("SET BY: AAAAA").height);
    	gameFont.setColor(1, 1, 1, 1);
    	gameFont.draw(batch, "MAIN MENU", (Gdx.graphics.getWidth()/2 - gameFont.getBounds("MAIN MENU").width)/2,
    			Gdx.graphics.getHeight()/2 - 9*gameFont.getBounds("MAIN MENU").height/2);
    	gameFont.draw(batch, "RETRY", Gdx.graphics.getWidth()*3/4 - gameFont.getBounds("RETRY").width/2,
    			Gdx.graphics.getHeight()/2 - 9*gameFont.getBounds("RETRY").height/2);
    	batch.end();
    }

    @Override
	public void update(GameScreen screen, float deltaTime) {
		if (Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth()/2 && -1*(Gdx.input.getY() - Gdx.graphics.getHeight()) < Gdx.graphics.getHeight()/2){
    		screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
    	}
		if (Gdx.input.justTouched() && Gdx.input.getX() > Gdx.graphics.getWidth()/2 && -1*(Gdx.input.getY() - Gdx.graphics.getHeight()) < Gdx.graphics.getHeight()/2){
			screen.reset();
    	}
	}

}