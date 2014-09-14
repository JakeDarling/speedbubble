package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateOverPacer implements GameState {
	
    private int score;
    private SpriteBatch batch;
    
    private Score highestScore;
    
    private BitmapFont gameFont;

    public GameStateOverPacer(GameScreen screen, int score) {
        this.score = score;
        batch = new SpriteBatch();
        
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.setScale(Gdx.graphics.getHeight()/ (gameFont.getBounds("ARCADE").height * 13) );
    	
    	if (screen.getGame().actionResolver.getSignedInGPGS()) {
    		screen.getGame().actionResolver.submitScoreGPGS((int)(score), "CgkIh-6d6poMEAIQAw");
    	}
    	
    	HighScores.writeHighScore(HighScores.PACER, Assets.name, score, true);
    	highestScore = HighScores.fetchHighScores(HighScores.PACER, false).first();
    }

    @Override
    public void draw (GameScreen screen, float delta) {
    	
    	Gdx.gl.glClearColor(0,1,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	batch.begin();
    	gameFont.setColor(1, 1, 1, 1);
    	gameFont.draw(batch, "PACER", (Gdx.graphics.getWidth() - gameFont.getBounds("PACER").width)/2,
    			Gdx.graphics.getHeight()/2 + 11*gameFont.getBounds("PACER").height/2);
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.draw(batch, "GAME OVER", (Gdx.graphics.getWidth() - gameFont.getBounds("GAME OVER").width)/2,
    			Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds("GAME OVER").height/2);
    	gameFont.draw(batch, "SCORE: " + score, (Gdx.graphics.getWidth() - gameFont.getBounds("SCORE: 50").width)/2,
    			Gdx.graphics.getHeight()/2 + 4*gameFont.getBounds("SCORE: 50").height/2);
    	gameFont.setColor(0,0,1,1);
    	gameFont.draw(batch, "HIGHEST SCORE: " + String.format("%.0f", highestScore.getScore()), (Gdx.graphics.getWidth() - gameFont.getBounds("HIGHEST SCORE: " + String.format("%.0f", highestScore.getScore())).width)/2,
    	        Gdx.graphics.getHeight()/2 - 1*gameFont.getBounds("HIGHEST SCORE: 00").height/2);
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
