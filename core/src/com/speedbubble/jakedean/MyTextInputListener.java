package com.speedbubble.jakedean;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
	
	public boolean enteredData = false;
	
	@Override
	public void input (String text) {
		for(int i = 0; i < text.length(); i++){
            if(Character.isWhitespace(text.charAt(i))){
            	text = text.substring(0, i) + "_" + text.substring(i+1, text.length());
            }
        }
		Assets.name = text;
		enteredData = true;
	}

	@Override
	public void canceled () {
		Assets.name = "name";
		enteredData = true;
	}
	
}
