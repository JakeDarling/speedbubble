package com.speedbubble.jakedean;

/** Interface for all game-modes
 * 
 * @author Dean
 *
 */
public interface GameMode {
	/** updates the specific game mode
	 * 
	 * @param screen     = GameScreen is passed in to allow switching between different states (i.e. pre-start, running, game-over, pause)
	 * @param deltaTime	 = the time that has passed from the last time the screen was rendered (i.e. 60 frames / second, delta = .016)
	 */
    public void update(GameScreen screen , float deltaTime);
    
    /** draws the sprites, backgrounds, and texts of specific game mode
     */
    public void draw();
    
    /** destroys the objects associated with the specific game mode to free memory allocation
     */
    public void dispose();

}
