package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameStateGetName implements GameState{
	
	private MyTextInputListener listener;
	private float score;
	private String title, success;
	
	public GameStateGetName(GameScreen screen, float score, String title, String success){
		this.score = score;
		listener = new MyTextInputListener();	
		this.title = title;
		this.success = success;
		
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

	@Override
	public void draw(GameScreen screen, float deltaTime) {
		Gdx.gl.glClearColor(0,0,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
