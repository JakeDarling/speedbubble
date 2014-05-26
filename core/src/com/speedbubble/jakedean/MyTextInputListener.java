package com.speedbubble.jakedean;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
	
	private String name;
	private float score;
	
	public MyTextInputListener(float s){
		score = s;
	}
	
	   @Override
	   public void input (String text) {
		   name = text;
		   HighScores.writeHighScore(name, score, false);
	   }

	   @Override
	   public void canceled () {
	   }
	   
	   public String getName(){
		   return name;
	   }
	}
