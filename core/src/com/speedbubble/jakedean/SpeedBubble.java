package com.speedbubble.jakedean;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpeedBubble extends Game {


	Boolean popStarted = false, gameOver = false, failure = false;

	
	SpriteBatch batch;
	
	float stateTime=0;
	
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
