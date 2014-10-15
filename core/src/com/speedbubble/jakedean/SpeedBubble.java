package com.speedbubble.jakedean;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/** Game Class
 * This is the main class for the game, on launch, the system will initialize this game class.
 * This class will check to make sure the user has the proper internal files on start up
 * If the user does not have the files, it will create them with "first play through" indicators inside
 * 		It will also set the screen to "BlackScreen" which will ask the user for their name and save it to a file
 * 
 * If the user has the files, it will assume they have already played the game, and therefore it will send the user right to a main menu
 * 
 * @author Dean
 *
 */
public class SpeedBubble extends Game {
	
	ActionResolver actionResolver;
	
	public SpeedBubble(ActionResolver ar){
		this.actionResolver = ar;
	}

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
        	Assets.load();
        	setScreen(new MainMenuScreen(this));
        }
	}

	@Override
	public void render () {
		super.render();
	}

	
}
