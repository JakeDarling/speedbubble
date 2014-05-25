package com.speedbubble.jakedean;

public interface GameState {
	
	public void update(GameScreen screen, float deltaTime);
	public void draw(GameScreen screen, float deltaTime);
}
