package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/** Screen class to be called the very first time the game is played. Will wait for a text input to acquire a username
 * 		and will make that the permanent username for the device, before calling assets.load
 * 
 * @author Dean
 *
 */
public class BlackScreen implements Screen{
	
	private SpeedBubble game;
	private MyTextInputListener listener;
	
	public BlackScreen(SpeedBubble sb){
		game = sb;
		listener = new MyTextInputListener();
		
		Gdx.input.getTextInput(listener, "PLEASE ENTER YOUR NAME", "");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	
    	if(listener.enteredData && listener.isValid){

			Gdx.files.local("firstPlay.txt").writeString(Assets.name + "\ntrue\ntrue\ntrue\ntrue\n", false);
			Assets.load();
    		game.setScreen(new MainMenuScreen(game));
    	}
    	else if(listener.enteredData && !listener.isValid){
    		Gdx.files.local("firstPlay.txt").writeString("name\ntrue\ntrue\ntrue\ntrue\n", false);
    		System.out.println("overwrote file");
    		game.setScreen(new MainMenuScreen(game));
    	}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
