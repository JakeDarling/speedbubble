package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

/** Screen class for when the user is in-game
 * 	This class holds information about the state of the game, and the mode the user has selected
 * 
 * @author Dean
 *
 */
public class GameScreen implements Screen {
	
	private SpeedBubble game;
	private GameModeEnum selector;
    private GameMode mode;
    private GameState state;

    public GameScreen(SpeedBubble sb, GameModeEnum selection) {
    	
    	// Stop the Android back-button from exiting the app, set custom behavior in render method
    	Gdx.input.setCatchBackKey(true);
        
    	// pass in the Speed Bubble game created when the app launched
    	game = sb;
    	
    	// remove ads from the top of the screen
    	sb.actionResolver.showAds(false);
    	
    	// MainMenuScreen passes in the Enum of the selected game mode, this sets the game mode accordingly
    	selector = selection;
        setMode(selector);
        
        // Start the game 
        setState(new GameStateStart(this));
        
    }
    
    /** At the end of the game, if the user clicks the "retry" button, this will recreate all of the objects necessary
     * 		for another play-through
     */
    public void reset(){
    	game.actionResolver.showAds(false);
    	setMode(selector);
        setState(new GameStateStart(this));
    }
    
    public SpeedBubble getGame(){
    	return game;
    }
    
    public GameState getState(){
    	return state;
    }
    
    /** Used by other classes to alternate between Game States
     * 
     * @param state
     */
    public void setState(GameState state) {
	    this.state = state;
    }
    
    public GameMode getMode(){
    	return mode;
    }
    
    public GameModeEnum getEnum(){
    	return selector;
    }
    
    public void setMode(GameModeEnum m){
    	switch (m){
    	case ARCADE:
    		mode = new GameModeArcade();
    		break;
    	case FIFTY_BUBBLE:
    		mode = new GameModeFiftyBubble();
    		break;
    	case TIMED:
    		mode = new GameModeTimed();
    		break;
    	case PACER:
    		mode = new GameModePacer();
    		break;
    	}
    }
    
    
    /** calls the methods to update and draw each state, checks if the back button was tapped to send user back to main menu
     */
    @Override
    public void render(float delta) {
        state.update(this, delta);
        state.draw(this, delta);
        
        if (Gdx.input.isKeyPressed(Keys.BACK) && !Settings.MAIN_MENU){
        	game.actionResolver.showAds(true);
        	game.setScreen(new MainMenuScreen(game));
		}
    }
    
    @Override
    public void dispose() {
    }
    
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void show() {
    	
    }
    @Override
    public void hide() {
    	
    }
    @Override
    public void pause() {
    	
    }
    @Override
    public void resume() {

    }
}
