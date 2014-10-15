package com.speedbubble.jakedean;

/** Game State for when the game is currently running
 * makes calls to mode.update and mode.draw dependent upon the mode that was created in GameScreen
 * 
 * @author Dean
 *
 */
public class GameStateRunning implements GameState{
	
	public void update(GameScreen screen, float deltaTime){
		screen.getMode().update(screen, deltaTime);
	}

	@Override
	public void draw(GameScreen screen, float deltaTime) {
		screen.getMode().draw();
	}

}
