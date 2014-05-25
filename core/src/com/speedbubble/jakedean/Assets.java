package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;

public class Assets {
    public static BitmapFont bubbleFontSmall, bubbleFontBig;

    public static Animation poppingBubble;
    public static TextureAtlas bubbleAtlas;
    public static TextureRegion[] bubbleFrames;
    public static TextureRegion currentBubble;

    public static Sound bubbleSound;

    public static Sprite bubble, previousBubble;

    public static void load() {
        bubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubble.mp3"));
        bubbleFontSmall = new BitmapFont(Gdx.files.internal("bubble.fnt"));
        bubbleFontSmall.setScale(.5f);
        bubbleFontBig = new BitmapFont(Gdx.files.internal("bubble.fnt"));

        bubbleAtlas = new TextureAtlas("bubbleAnimation.pack");
        bubbleFrames = new TextureRegion[5];

        for (int i = 0; i<5; i++){
            bubbleFrames[i] = bubbleAtlas.findRegion("bubble"+i);
        }

        poppingBubble = new Animation(.01f, bubbleFrames);

        bubble = new Sprite(bubbleAtlas.findRegion("bubble0"));
        relocateBubble();
        bubble.setSize(200, 200);
        previousBubble = new Sprite(bubbleAtlas.findRegion("bubble0"));
        previousBubble.setSize(200,200);
    }

    public static void relocateBubble(){
        bubble.setPosition(MathUtils.random(0, Gdx.graphics.getWidth() - bubble.getWidth()), MathUtils.random(0,
                Gdx.graphics.getHeight()-bubble.getHeight()));
    }
}
