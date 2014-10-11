package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateGameOver implements GameState{
	
	private String title, success, score, highestScore, setBy, main, retry;
	private Score high;
	private SpriteBatch batch;
	private BitmapFont gameFont;
	private Color color;
	
	public GameStateGameOver(GameScreen screen, int score, String title, String success){
		
		
		
		screen.getGame().actionResolver.showAds(true);
        batch = new SpriteBatch();
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.setScale(Gdx.graphics.getHeight()/ (gameFont.getBounds("ARCADE").height * 13) );
		
		this.title = title;
		this.success = success;
		this.main = "MAIN MENU";
		this.retry = "RETRY";
		
		if(success.equals("SUCCESS!")) {
			this.score = "SCORE: " + score;
			color = new Color(0,1,0,1);
			
			switch (screen.getEnum()){
			case ARCADE:
				if (screen.getGame().actionResolver.getSignedInGPGS()) {
		    		screen.getGame().actionResolver.submitScoreGPGS((int)(score), "CgkIh-6d6poMEAIQDA");
		    	}
		    	HighScores.writeHighScore(HighScores.ARCADE, Assets.name, score, true);
				high = HighScores.fetchHighScores(HighScores.ARCADE, false).first();
	    		break;
	    	case TIMED:
	    		if (screen.getGame().actionResolver.getSignedInGPGS()) {
	        		screen.getGame().actionResolver.submitScoreGPGS((int)(score), "CgkIh-6d6poMEAIQBA");
	        	}
	        	HighScores.writeHighScore(HighScores.TIMED, Assets.name, score, true);
	    		high = HighScores.fetchHighScores(HighScores.TIMED, false).first();
	    		break;
	    	case PACER:
	    		if (screen.getGame().actionResolver.getSignedInGPGS()) {
	        		screen.getGame().actionResolver.submitScoreGPGS((int)(score), "CgkIh-6d6poMEAIQAw");
	        	}
	        	HighScores.writeHighScore(HighScores.PACER, Assets.name, score, true);
	    		high = HighScores.fetchHighScores(HighScores.PACER, false).first();
	    		break;
			default:
				break;
			}
		}
		else {
			this.score = "SCORE: 0";
			color = new Color(1,0,0,1);
			
			switch (screen.getEnum()){
			case ARCADE:
				high = HighScores.fetchHighScores(HighScores.ARCADE, false).first();
	    		break;
	    	case TIMED:
	    		high = HighScores.fetchHighScores(HighScores.TIMED, false).first();
	    		break;
	    	case PACER:
	    		high = HighScores.fetchHighScores(HighScores.PACER, false).first();
	    		break;
			default:
				break;
			}
		}
		
		this.highestScore = "HIGHEST SCORE: " + String.format("%.0f", high.getScore());
		this.setBy = "SET BY: " + high.getName();
		
	}
	
	public GameStateGameOver(GameScreen screen, float score, String title, String success){
		
		screen.getGame().actionResolver.showAds(true);
        batch = new SpriteBatch();
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.setScale(Gdx.graphics.getHeight()/ (gameFont.getBounds("ARCADE").height * 13) );
		
		this.title = title;
		this.success = success;
		this.main = "MAIN MENU";
		this.retry = "RETRY";
		
		if(success.equals("SUCCESS!")) {
			this.score = "SCORE: " + String.format("%.3f", score);
			color = new Color(0,1,0,1);
			if (screen.getGame().actionResolver.getSignedInGPGS()) {
	    		screen.getGame().actionResolver.submitScoreGPGS((long)(score*1000), "CgkIh-6d6poMEAIQCw");
	    	}
	    	HighScores.writeHighScore(HighScores.FIFTY, Assets.name, score, false);
		}
		else {
			this.score = "SCORE: 0.000";
			color = new Color(1,0,0,1);
		}
		
		high = HighScores.fetchHighScores(HighScores.FIFTY, false).first();
		this.highestScore = "HIGHEST SCORE: " + String.format("%.3f", high.getScore());
		this.setBy = "SET BY: " + high.getName();
	}

	@Override
	public void update(GameScreen screen, float deltaTime) {
		if (Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth()/2 
				&& -1*(Gdx.input.getY() - Gdx.graphics.getHeight()) < Gdx.graphics.getHeight()/2){
    		screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
    	}
		if (Gdx.input.justTouched() && Gdx.input.getX() > Gdx.graphics.getWidth()/2 
				&& -1*(Gdx.input.getY() - Gdx.graphics.getHeight()) < Gdx.graphics.getHeight()/2){
			screen.reset();
    	}
	}

	@Override
	public void draw(GameScreen screen, float deltaTime) {
		
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	batch.begin();
    	gameFont.setColor(1, 1, 1, 1);
    	gameFont.draw(batch, title, (Gdx.graphics.getWidth() - gameFont.getBounds(title).width)/2,
    	        Gdx.graphics.getHeight()/2 + 10*gameFont.getBounds(title).height/2);
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.draw(batch, success, (Gdx.graphics.getWidth() - gameFont.getBounds(success).width)/2,
    	        Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds(success).height/2);
    	gameFont.draw(batch, score, (Gdx.graphics.getWidth() - gameFont.getBounds(score).width)/2,
    	        Gdx.graphics.getHeight()/2 + 4*gameFont.getBounds(score).height/2);
    	gameFont.setColor(1,1,1,1);
    	gameFont.draw(batch, highestScore, (Gdx.graphics.getWidth() - gameFont.getBounds(highestScore).width)/2,
    	        Gdx.graphics.getHeight()/2 - 1*gameFont.getBounds(highestScore).height/2);
    	gameFont.draw(batch, setBy, (Gdx.graphics.getWidth() - gameFont.getBounds(setBy).width)/2,
    	        Gdx.graphics.getHeight()/2 - 2*gameFont.getBounds(setBy).height);
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.draw(batch, main, (Gdx.graphics.getWidth()/2 - gameFont.getBounds(main).width)/2,
    			Gdx.graphics.getHeight()/2 - 9*gameFont.getBounds(main).height/2);
    	gameFont.draw(batch, retry, Gdx.graphics.getWidth()*3/4 - gameFont.getBounds(retry).width/2,
    			Gdx.graphics.getHeight()/2 - 9*gameFont.getBounds(retry).height/2);
    	batch.end();
	}

}
