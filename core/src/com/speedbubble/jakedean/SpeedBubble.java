package com.speedbubble.jakedean;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class SpeedBubble extends Game {

	@Override
	public void create () {
        HighScores.createLocalFiles();
        
        if(!Gdx.files.local("firstPlay.txt").exists()){
    		try {
				Gdx.files.local("firstPlay.txt").file().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		setScreen(new BlackScreen(this));
    	}
        else{
        	setScreen(new MainMenuScreen(this));
        }
	}

	@Override
	public void render () {
		super.render();
	}

	
}
