package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.speedbubble.jakedean.MainMenuScreen.Mode;

public class GameScreen implements Screen {

    private SpeedBubble sb;
    private SpriteBatch batch;

    private GameMode mode;

    private boolean popStarted;
    private float timeLeft;
    private float stateTime;
    private int score;

    public GameScreen(SpeedBubble s, Mode m) {
        sb = s;
        stateTime = 0;
        timeLeft = 15f;
        popStarted = false;
        batch = new SpriteBatch();
    }

    private void update() {
        stateTime += Gdx.graphics.getDeltaTime();
        timeLeft -= Gdx.graphics.getDeltaTime();

        //mode.update();
        if (Gdx.input.justTouched()){
            if(Gdx.input.getX()>=Assets.bubble.getX() && Gdx.input.getX() <= Assets.bubble.getX()+Assets.bubble.getWidth()
                    && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) >= Assets.bubble.getY() && ((Gdx.input.getY() - Gdx.graphics.getHeight()) * -1) <= Assets.bubble.getY()+Assets.bubble.getHeight()){
                stateTime=0;
                Assets.previousBubble.setPosition(Assets.bubble.getX(), Assets.bubble.getY());
                popStarted=true;
                Assets.relocateBubble();
                Assets.bubbleSound.play();
                score++;
            }
            else{ sb.setScreen(new FailureScreen(sb, score)); }
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);


        update();
        batch.begin();
        if(popStarted) drawAnimation();
        Assets.bubble.draw(batch);
        Assets.bubbleFontSmall.draw(batch, "BUBBLES POPPED: " + score, 0,
                Gdx.graphics.getHeight());
        Assets.bubbleFontSmall.draw(batch, "TIME REMAINING: " + String.format("%.2f", timeLeft), (Gdx.graphics.getWidth() - Assets.bubbleFontSmall.getBounds("TIME REMAINING:  ").width)/2,
                Gdx.graphics.getHeight());
        batch.end();
        if (timeLeft < 0){ sb.setScreen(new GameOverScreen(sb, score)); }
    }


    public void drawAnimation(){
        Assets.currentBubble = Assets.poppingBubble.getKeyFrame(stateTime, false);
        batch.draw(Assets.currentBubble, Assets.previousBubble.getX(), Assets.previousBubble.getY(), Assets.previousBubble.getWidth(), Assets.previousBubble.getHeight());
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
