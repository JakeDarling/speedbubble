package com.speedbubble.jakedean;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	private SpeedBubble game;
	public GameModeEnum selector;
    private GameMode mode;
    private GameState state;

    public GameScreen(SpeedBubble sb, GameModeEnum selection) {
        
    	game = sb;
    	selector = selection;
        setMode(selector);
        setState(new GameStateStart());
        
    }
    
    public void reset(){
    	setMode(selector);
        setState(new GameStateStart());
    }
    
    public SpeedBubble getGame(){
    	return game;
    }
    
    public GameState getState(){
    	return state;
    }
    
    public void setState(GameState state) {
	    this.state = state;
    }
    
    public GameMode getMode(){
    	return mode;
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
    
    
    
    @Override
    public void render(float delta) {
        state.update(this, delta);
        state.draw(this, delta);
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
//    	setState(new GameStatePaused());
    }
    @Override
    public void resume() {

    }
}
