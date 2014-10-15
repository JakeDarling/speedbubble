package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Game State for when the game has come to its conclusion. This will be created AFTER the game verifies whether the 
 * 	user has set a new high score and taken their name.
 * 
 * 	During this state, the high scores will be updated using the name determined by the "get name" game state.
 * 	The user will be displayed an end of game screen demonstrating a failure or a successful game.
 * 	The game will wait for the user to click main menu, retry, or the android back-button before proceeding.
 * 
 * @author Dean
 *
 */
public class GameStateGameOver implements GameState{
	
	private String title, success, score, highestScore, setBy, main, retry;
	private Score high;
	private SpriteBatch batch;
	private BitmapFont gameFont;
	private Color color;
	
	/** Creates a game over screen with an INT for a score
	 * 
	 * @param screen 	The game screen, necessary for reseting the game if a user clicks retry
	 * @param score		The score that the user has just posted
	 * @param title		The title of the game-mode that the user was playing
	 * @param success	Whether or not the user was successful in completing their previous game
	 */
	public GameStateGameOver(GameScreen screen, int score, String title, String success){
		
		// Shows ads now that we are out of the game
		screen.getGame().actionResolver.showAds(true);
		
		// creates libgdx variables
        batch = new SpriteBatch();
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.setScale(Gdx.graphics.getHeight()/ (gameFont.getBounds("ARCADE").height * 13) );
		
    	// sets strings
		this.title = title;
		this.success = success;
		this.main = "MAIN MENU";
		this.retry = "RETRY";
		
		// checks to see if the game was completed successfully or failed, if successful it will submit scores to google leaderboard
		//		and to the local high scores on the phone, "high" is the best local score, set screen color green
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
		// if the game mode was failed, it will not submit the score to google or to local high scores
		//		"high" is the best local score, set screen color red
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
		
		// sets the rest of the strings
		this.highestScore = "HIGHEST SCORE: " + String.format("%.0f", high.getScore());
		this.setBy = "SET BY: " + high.getName();
		
	}
	
	
	/** Creates a game over screen with an FLOAT for a score, the only game mode with this option is Rapid 25
	 * 
	 * @param screen 	The game screen, necessary for reseting the game if a user clicks retry
	 * @param score		The score that the user has just posted
	 * @param title		The title of the game-mode that the user was playing
	 * @param success	Whether or not the user was successful in completing their previous game
	 */
	public GameStateGameOver(GameScreen screen, float score, String title, String success){
		
		// show ads now that game is over
		screen.getGame().actionResolver.showAds(true);
		
		// create libgdx variables
        batch = new SpriteBatch();
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(0, 0, 0, 1);
    	gameFont.setScale(Gdx.graphics.getHeight()/ (gameFont.getBounds("ARCADE").height * 13) );
		
    	// set strings
		this.title = title;
		this.success = success;
		this.main = "MAIN MENU";
		this.retry = "RETRY";
		
		// submit score only if the game was a success, set screen color green
		if(success.equals("SUCCESS!")) {
			this.score = "SCORE: " + String.format("%.3f", score);
			color = new Color(0,1,0,1);
			if (screen.getGame().actionResolver.getSignedInGPGS()) {
	    		screen.getGame().actionResolver.submitScoreGPGS((long)(score*1000), "CgkIh-6d6poMEAIQCw");
	    	}
	    	HighScores.writeHighScore(HighScores.FIFTY, Assets.name, score, false);
		}
		// do not submit scores, set screen color red
		else {
			this.score = "SCORE: 0.000";
			color = new Color(1,0,0,1);
		}
		
		// get the highest score from the local high scores file
		high = HighScores.fetchHighScores(HighScores.FIFTY, false).first();
		
		// set the rest of the strings
		this.highestScore = "HIGHEST SCORE: " + String.format("%.3f", high.getScore());
		this.setBy = "SET BY: " + high.getName();
	}
	
	/**Update the game over state, if the user clicks main menu -> send to main menu, if clicks retry -> reset the game
	 */
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
	
	/** Draw all of the strings to the screen to let the user know how they performed
	 */
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
