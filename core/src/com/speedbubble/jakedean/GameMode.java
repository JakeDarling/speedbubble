package com.speedbubble.jakedean;

public interface GameMode {
	
    public void update(GameScreen screen , float deltaTime);
    public void draw();
    public void dispose();

}
