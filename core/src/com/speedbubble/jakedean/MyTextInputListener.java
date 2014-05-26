package com.speedbubble.jakedean;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
	
	public boolean enteredData = false;
	
	@Override
	public void input (String text) {
		Assets.name = text;
		enteredData = true;
	}

	@Override
	public void canceled () {
		Assets.name = "name";
		enteredData = true;
	}
	
}
