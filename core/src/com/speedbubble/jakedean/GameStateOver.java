package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateOver implements GameState {

    private int score;
    private SpriteBatch batch;

    public GameStateOver(int score) {
        this.score = score;
        batch = new SpriteBatch();
    }

    @Override
    public void draw (GameScreen screen, float delta) {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        Assets.bubbleFontBig.draw(batch, "GAME OVER", (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("GAME OVER").width)/2,
                Gdx.graphics.getHeight()/2 + (float)2.5*Assets.bubbleFontBig.getBounds("GAME OVER").height);
        Assets.bubbleFontBig.draw(batch, "FINAL SCORE: " + score, (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("FINAL SCORE:  ").width)/2,
                Gdx.graphics.getHeight()/2 + Assets.bubbleFontBig.getBounds("FINAL SCORE:  ").height/2);
        batch.end();
    }

	@Override
	public void update(GameScreen screen, float deltaTime) {
		if (Gdx.input.justTouched()){
    		screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));;
    	}
	}
    
    
}
