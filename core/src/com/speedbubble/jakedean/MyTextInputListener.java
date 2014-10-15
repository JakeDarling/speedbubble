package com.speedbubble.jakedean;

import com.badlogic.gdx.Input.TextInputListener;

/** Text input listener class, only works with Android and Desktop
 * 	waits for user to enter data or click cancel before registering that data was entered.
 * 	The booleans are used in other classes to make sure something was entered before continuing
 * 
 * @author Dean
 *
 */
public class MyTextInputListener implements TextInputListener {
	
	public boolean enteredData = false;
	public boolean isValid = true;
	
	@Override
	public void input (String text) {
		
		// removes any spaces and replaces them with underscores because the methods in HighScores class break strings apart by spaces 
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
		isValid = false;
		enteredData = true;
	}
	
}
