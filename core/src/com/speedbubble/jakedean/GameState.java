package com.speedbubble.jakedean;

/** Interface for each game state
 * 
 * @author Dean
 *
 */
public interface GameState {
	
	// This will check for user input, and perform all other methods necessary to update the game
	public void update(GameScreen screen, float deltaTime);
	
	// This will draw everything to screen
	public void draw(GameScreen screen, float deltaTime);
}
