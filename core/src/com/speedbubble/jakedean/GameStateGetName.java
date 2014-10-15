package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** game state to get the name of the user if they had a successful game and it set a personal top 5 score
 * 	This state is necessary otherwise the user would not be able to input their name
 * 
 * @author Dean
 *
 */
public class GameStateGetName implements GameState{
	
	private MyTextInputListener listener;
	private float score;
	private String title, success;
	
	public GameStateGetName(GameScreen screen, float score, String title, String success){
		this.score = score;
		this.title = title;
		this.success = success;
		
		listener = new MyTextInputListener();
		
		// checks to make sure the game was completed successfully, and that the score is in the top 5 for the specific mode
		// 		will pull up the keyboard and wait for user input, then store the name provided in Assets
		if(success.equals("SUCCESS!")){
			switch(screen.getEnum()){
			case TIMED:
				if(score >= HighScores.fetchHighScores(HighScores.TIMED, true).first().getScore()){
					Gdx.input.getTextInput(listener, "TOP 5 SCORE! ENTER NAME", Assets.name);
				}
				else{listener.enteredData = true;}
				break;
			case FIFTY_BUBBLE:
				if(score <= HighScores.fetchHighScores(HighScores.FIFTY, true).first().getScore()){
					Gdx.input.getTextInput(listener, "TOP 5 SCORE! ENTER NAME", Assets.name);
				}
				else{listener.enteredData = true;}
				break;
			case ARCADE:
				if(score >= HighScores.fetchHighScores(HighScores.ARCADE, true).first().getScore()){
					Gdx.input.getTextInput(listener, "TOP 5 SCORE! ENTER NAME", Assets.name);
				}
				else{listener.enteredData = true;}
				break;
			case PACER:
				if(score >= HighScores.fetchHighScores(HighScores.PACER, true).first().getScore()){
					Gdx.input.getTextInput(listener, "TOP 5 SCORE! ENTER NAME", Assets.name);
				}
				else{listener.enteredData = true;}
				break;
			}
		}
		else{listener.enteredData = true;}
	}
	
	// waits for the user to input their name if need be, Rapid 25 mode needs to submit their score as a float
	// all other modes submit score as int, passes score, game mode title, and the success string to a game over state
	@Override
	public void update(GameScreen screen, float deltaTime) {
		if(listener.enteredData){
			switch(screen.getEnum()){
			case FIFTY_BUBBLE:
				screen.setState(new GameStateGameOver(screen, (float) score, title, success));
				break;
			default:
				screen.setState(new GameStateGameOver(screen, (int) score, title, success));
				break;
			}
		}
	}
	
	//draws a black screen in the background while waiting for text input
	@Override
	public void draw(GameScreen screen, float deltaTime) {
		Gdx.gl.glClearColor(0,0,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
