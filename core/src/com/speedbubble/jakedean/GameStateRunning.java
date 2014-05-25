package com.speedbubble.jakedean;

public class GameStateRunning implements GameState{
	
	public void update(GameScreen screen, float deltaTime){
		screen.getMode().update(screen, deltaTime);
	}

	@Override
	public void draw(GameScreen screen, float deltaTime) {
		screen.getMode().draw();
	}

}
