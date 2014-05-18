package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FailureScreen implements Screen {

    private SpriteBatch batch;
    private int score;

    public FailureScreen(SpeedBubble sb, int score) {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        Assets.bubbleFontBig.draw(batch, "FAILURE", (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("FAILURE").width)/2,
                Gdx.graphics.getHeight()/2 + (float) 2.5*Assets.bubbleFontBig.getBounds("FAILURE").height);
        Assets.bubbleFontBig.draw(batch, "FINAL SCORE: " + score, (Gdx.graphics.getWidth() - Assets.bubbleFontBig.getBounds("FINAL SCORE:  ").width)/2,
                Gdx.graphics.getHeight()/2 + Assets.bubbleFontBig.getBounds("FINAL SCORE:  ").height/2);
        batch.end();
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

    @Override
    public void dispose() {

    }
}
