package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;

public class Assets {

    public static Animation poppingBubble;
    public static TextureAtlas bubbleAtlas;
    public static TextureRegion[] bubbleFrames;
    public static TextureRegion currentBubble;
    public static BitmapFont gameFont;
    
    public static String name;

    public static Sound bubbleSound, failSound;

    public static Sprite bubble, previousBubble;

    public static void load() {    	
    	
        bubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubble.mp3"));
        failSound = Gdx.audio.newSound(Gdx.files.internal("largeBubble.mp3"));
        
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));

        bubbleAtlas = new TextureAtlas("bubbleAnimation.pack");
        bubbleFrames = new TextureRegion[5];

        for (int i = 0; i<5; i++){
            bubbleFrames[i] = bubbleAtlas.findRegion("bubble"+i);
        }

        poppingBubble = new Animation(.02f, bubbleFrames);

        bubble = new Sprite(bubbleAtlas.findRegion("bubble0"));
        relocateBubble();
        bubble.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
        previousBubble = new Sprite(bubbleAtlas.findRegion("bubble0"));
        previousBubble.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
    }

    public static void relocateBubble(){
        bubble.setPosition(MathUtils.random(0, Gdx.graphics.getWidth() - bubble.getWidth()), MathUtils.random(0,
                Gdx.graphics.getHeight()-bubble.getHeight()- 2*gameFont.getBounds("HI").height));
    }
    
    public static void playSound(Sound s){
		if (Settings.soundEnabled){
			s.play();
		}
	}
    
    public static void playLoopingSound(Sound s){
    	if (Settings.soundEnabled){
    		s.loop();
    	}
    }
}
