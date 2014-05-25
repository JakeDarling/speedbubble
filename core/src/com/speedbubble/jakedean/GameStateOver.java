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
        Assets.bubbleFontBig.draw(batch, "MAIN MENU", (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("MAIN MENU").width)/4,
                (float)2.5*Assets.bubbleFontBig.getBounds("MAIN MENU").height);
        Assets.bubbleFontBig.draw(batch, "RETRY", (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("RETRY").width)*3/4,
                (float)2.5*Assets.bubbleFontBig.getBounds("RETRY").height);
        batch.end();
    }

	@Override
	public void update(GameScreen screen, float deltaTime) {
		if (Gdx.input.justTouched() && Gdx.input.getX() > (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("MAIN MENU").width)/4 &&
				Gdx.input.getX() < (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("MAIN MENU").width)/4 + Assets.bubbleFontBig.getBounds("MAIN MENU").width &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) > (float)2.5*Assets.bubbleFontBig.getBounds("MAIN MENU").height - Assets.bubbleFontBig.getBounds("MAIN MENU").height &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) < (float)2.5*Assets.bubbleFontBig.getBounds("MAIN MENU").height){
    		screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
    	}
		if (Gdx.input.justTouched() && Gdx.input.getX() > (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("RETRY").width)*3/4 &&
				Gdx.input.getX() < (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("RETRY").width)*3/4 + Assets.bubbleFontBig.getBounds("RETRY").width &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) > (float)2.5*Assets.bubbleFontBig.getBounds("RETRY").height - Assets.bubbleFontBig.getBounds("RETRY").height &&
				((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) < (float)2.5*Assets.bubbleFontBig.getBounds("RETRY").height){
			screen.reset();
    	}
	}
    
    
}
