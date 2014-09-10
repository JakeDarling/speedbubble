package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

public class GameStateGetName implements GameState{
	
	MyTextInputListener listener;
	float score;
	Array<Score> scoreArray;
	
	public GameStateGetName(GameScreen screen, float score){
		this.score = score;
		listener = new MyTextInputListener();		
		
		switch(screen.selector){
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

	@Override
	public void update(GameScreen screen, float deltaTime) {
		if(listener.enteredData){
			switch(screen.selector){
			case TIMED:
				screen.setState(new GameStateOverTimed(screen, (int) score));
				break;
			case FIFTY_BUBBLE:
				screen.setState(new GameStateOverFB(screen, score));
				break;
			case ARCADE:
				screen.setState(new GameStateOverArcade(screen, (int) score));
				break;
			case PACER:
				screen.setState(new GameStateOverPacer(screen, (int) score));
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
