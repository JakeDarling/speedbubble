package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;

public class Assets {

    public static Animation poppingBubble;
    public static TextureAtlas bubbleAtlas, greenAtlas;
    public static TextureRegion[] bubbleFrames;
    public static TextureRegion currentBubble;
    public static BitmapFont gameFont;
    
    public static String name, text;
    public static String[] lines;
    
    public static boolean firstPlayArcade, firstPlayFifty, firstPlayPacer, firstPlayTimed;

    public static Sound bubbleSound, failSound;

    public static Sprite bubble, previousBubble, phantom;
    
    public static final int BLUE=0, YELLOW=1, ORANGE=2, RED=3, PINK=4, PURPLE=5;
    
    /**
     * called at the creation of the game to load these assets
     * these are only the universal assets used by every screen/mode
     */
    public static void load() {   
    	text = Gdx.files.local("firstPlay.txt").readString();
        lines = text.split("\n");
        
    	name = lines[0];
    	if (lines[1].equals("true")) firstPlayArcade = true;
    	else firstPlayArcade = false;
    	if (lines[2].equals("true")) firstPlayFifty = true;
    	else firstPlayFifty = false;
    	if (lines[3].equals("true")) firstPlayPacer = true;
    	else firstPlayPacer = false;
    	if (lines[4].equals("true")) firstPlayTimed = true;
    	else firstPlayPacer = false;
    	
        bubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubble.mp3"));
        failSound = Gdx.audio.newSound(Gdx.files.internal("largeBubble.mp3"));
        
        gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
        
        
        greenAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationGreen.pack");
        bubbleFrames = new TextureRegion[5];
        for (int i = 0; i<5; i++){
            bubbleFrames[i] = greenAtlas.findRegion("bubble"+i);
        }
        poppingBubble = new Animation(.025f, bubbleFrames);
        
        
        phantom = new Sprite();
        bubble = new Sprite();
        previousBubble = new Sprite();
        
        setBubbleColor(BLUE);
        
        phantom.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
        phantom.setPosition((Gdx.graphics.getWidth() - phantom.getWidth())/2, (Gdx.graphics.getHeight() - phantom.getHeight())/2);
        phantom.setColor(1, 1, 1, .2f);
        bubble.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
        previousBubble.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
    }
    
    public static void setBubbleColor(int color){
    	
    	switch(color){
    	case BLUE:
    		bubbleAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationBlue.pack");
            break;
    	case YELLOW:
    		bubbleAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationYellow.pack");
            break;
    	case ORANGE:
    		bubbleAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationOrange.pack");
            break;
    	case RED:
    		bubbleAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationRed.pack");
            break;
    	case PINK:
    		bubbleAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationPink.pack");
            break;
    	case PURPLE:
    		bubbleAtlas = new TextureAtlas("bubbleAnimation/bubbleAnimationPurple.pack");
            break;
    	}
        
        phantom.setRegion(bubbleAtlas.findRegion("bubblePhantom"));
        bubble.setRegion(bubbleAtlas.findRegion("bubble0"));
        previousBubble.setRegion(bubbleAtlas.findRegion("bubble0"));
    }

    public static void relocateBubble(){
    	
    	bubble.setPosition(phantom.getX(), phantom.getY());
    	
        phantom.setPosition(MathUtils.random(0, Gdx.graphics.getWidth() - bubble.getWidth()), MathUtils.random(0,
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
