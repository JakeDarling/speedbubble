package com.speedbubble.jakedean;

import com.badlogic.gdx.Game;

public class SpeedBubble extends Game {

	@Override
	public void create () {
        Assets.load();

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	
}
