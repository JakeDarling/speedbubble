package com.speedbubble.jakedean;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

//import com.google.example.games.basegameutils;

public class SpeedBubble extends Game {

	@Override
	public void create () {
//		GoogleApiClient.Builder builder = 
//		        new GoogleApiClient.Builder(this, this, this);
//		    builder.addApi(Games.API)
//		           .addApi(Plus.API)
//		           .addApi(AppStateManager.API)
//		           .addScope(Games.SCOPE_GAMES)
//		           .addScope(Plus.SCOPE_PLUS_LOGIN)
//		           .addScope(AppStateManager.SCOPE_APP_STATE);
//		    mClient = builder.build();
		    
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
