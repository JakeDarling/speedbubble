package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Game state class for pre-game instructions and "tap to start" screens
 * 
 * @author Dean
 *
 */
public class GameStateStart implements GameState {
	
	private SpriteBatch batch;
	
	private BitmapFont gameFont;
	
	public GameStateStart(GameScreen screen){
		batch = new SpriteBatch();
		
		gameFont = new BitmapFont(Gdx.files.internal("gameFont.fnt"));
    	gameFont.setColor(1, 1, 1, 1);
    	
    	
    	/**This will  check the file "firsPlayed.txt" to see if this is the first time this specific game mode has been played.
    	 * 	if it is the first time the game mode has been played, it will present instructions on how to play the game.
    	 * 	If it is not the first time this mode has been played, it will say "tap anywhere to begin"
    	 * 
    	 */
    	switch (screen.getEnum()){
		case ARCADE:
			if (Assets.lines[1].equals("true")){
					gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("HOW LONG CAN YOU LAST? JUST TOUCH THE BUBBLES TO POP THEM!").width-.1f);
			}
			else gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("TAP ANYWHERE TO BEGIN").width -.1f);
			break;
		case FIFTY_BUBBLE:
			if (Assets.lines[2].equals("true")){
					gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("THE TIME WILL INCREASE UNTIL ALL BUBBLES ARE POPPED!").width-.1f);
			}
			else gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("TAP ANYWHERE TO BEGIN").width -.1f);
			break;
		case PACER:
			if (Assets.lines[3].equals("true")){
					gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("POP 25 BUBBLES IN THE GIVEN TIME TO ADVANCE!").width-.1f);
			}
			else gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("TAP ANYWHERE TO BEGIN").width -.1f);
			break;
		case TIMED:
			if (Assets.lines[4].equals("true")){
					gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("YOU HAVE 15 SECONDS TO SEE HOW MANY BUBBLES YOU CAN POP!").width-.1f);
			}
			else gameFont.setScale(Gdx.graphics.getWidth()/gameFont.getBounds("TAP ANYWHERE TO BEGIN").width -.1f);
			break;
		}
	}
	
	/** if it was the first play through of a certain mode, it will manually write to a file to make sure the instructions
	 * 		do not show the next time the user plays that mode
	 * 
	 * waits for the user to tap the screen and sets the game state to running state
	 */
	public void update(GameScreen screen, float deltaTime){
		
		switch (screen.getEnum()){
		case ARCADE:
			if (Gdx.input.justTouched()){
	    		Assets.lines[1] = "false";
	    		Gdx.files.local("firstPlay.txt").writeString(Assets.lines[0] +"\n" + Assets.lines[1] + "\n" + Assets.lines[2] +"\n" + Assets.lines[3] + "\n" + Assets.lines[4] +"\n", false);
	    		screen.setState(new GameStateRunning());
			}
			break;
		case FIFTY_BUBBLE:
			if (Gdx.input.justTouched()){
	    		Assets.lines[2] = "false";
	    		Gdx.files.local("firstPlay.txt").writeString(Assets.lines[0] +"\n" + Assets.lines[1] + "\n" + Assets.lines[2] +"\n" + Assets.lines[3] + "\n" + Assets.lines[4] +"\n", false);
	    		screen.setState(new GameStateRunning());
			}
			break;
		case PACER:
			if (Gdx.input.justTouched()){
	    		Assets.lines[3] = "false";
	    		Gdx.files.local("firstPlay.txt").writeString(Assets.lines[0] +"\n" + Assets.lines[1] + "\n" + Assets.lines[2] +"\n" + Assets.lines[3] + "\n" + Assets.lines[4] +"\n", false);
	    		screen.setState(new GameStateRunning());
			}
			break;
		case TIMED:
			if (Gdx.input.justTouched()){
	    		Assets.lines[4] = "false";
	    		Gdx.files.local("firstPlay.txt").writeString(Assets.lines[0] +"\n" + Assets.lines[1] + "\n" + Assets.lines[2] +"\n" + Assets.lines[3] + "\n" + Assets.lines[4] +"\n", false);
	    		screen.setState(new GameStateRunning());
			}
			break;
		}
	}
	
	/** Draws the specific instructions for each game mode or "tap anywhere to begin"
	 */
	public void draw(GameScreen screen, float deltaTime){
		Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
		switch (screen.getEnum()){
		case ARCADE:
			if (Assets.lines[1].equals("true")){
				gameFont.draw(batch, "POP THE LEADING BUBBLE!", (Gdx.graphics.getWidth() - gameFont.getBounds("POP THE LEADING BUBBLE!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "CAREFUL, THE LONGER YOU LAST THE FASTER THEY DROP!", (Gdx.graphics.getWidth() - gameFont.getBounds("CAREFUL, THE LONGER YOU LAST THE FASTER THEY DROP!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 5*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "HOW LONG CAN YOU LAST? JUST TOUCH THE BUBBLES TO POP THEM!", (Gdx.graphics.getWidth() - gameFont.getBounds("HOW LONG CAN YOU LAST? JUST TOUCH THE BUBBLES TO POP THEM!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 3*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
			}
			break;
		case FIFTY_BUBBLE:
			if (Assets.lines[2].equals("true")){
				gameFont.draw(batch, "HOW FAST CAN YOU POP 25 BUBBLES?", (Gdx.graphics.getWidth() - gameFont.getBounds("HOW FAST CAN YOU POP 50 BUBBLES?").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "THE TIME WILL INCREASE UNTIL ALL BUBBLES ARE POPPED!", (Gdx.graphics.getWidth() - gameFont.getBounds("THE TIME WILL INCREASE UNTIL ALL BUBBLES ARE POPPED!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 5*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "JUST TOUCH THE BUBBLES TO POP THEM!", (Gdx.graphics.getWidth() - gameFont.getBounds("JUST TOUCH THE BUBBLES TO POP THEM!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 3*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
			}
			break;
		case PACER:
			if (Assets.lines[3].equals("true")){
				gameFont.draw(batch, "POP 25 BUBBLES IN THE GIVEN TIME TO ADVANCE!", (Gdx.graphics.getWidth() - gameFont.getBounds("POP 25 BUBBLES IN THE GIVEN TIME TO ADVANCE!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "THE FURTHER YOU GO, THE LESS TIME YOU HAVE!", (Gdx.graphics.getWidth() - gameFont.getBounds("THE FURTHER YOU GO, THE LESS TIME YOU HAVE!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 5*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "JUST TOUCH THE BUBBLES TO POP THEM!", (Gdx.graphics.getWidth() - gameFont.getBounds("JUST TOUCH THE BUBBLES TO POP THEM!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 3*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
			}
			break;
		case TIMED:
			if (Assets.lines[4].equals("true")){
				gameFont.draw(batch, "YOU HAVE 15 SECONDS TO SEE HOW MANY BUBBLES YOU CAN POP!", (Gdx.graphics.getWidth() - gameFont.getBounds("YOU HAVE 15 SECONDS TO SEE HOW MANY BUBBLES YOU CAN POP!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 7*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "WHEN THE TIMER REACHES 0, THE GAME IS OVER", (Gdx.graphics.getWidth() - gameFont.getBounds("WHEN THE TIMER REACHES 0, THE GAME IS OVER").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 5*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
				gameFont.draw(batch, "JUST TOUCH THE BUBBLES TO POP THEM!", (Gdx.graphics.getWidth() - gameFont.getBounds("JUST TOUCH THE BUBBLES TO POP THEM!").width) / 2,
		        		Gdx.graphics.getHeight()/2 + 3*gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
			}
			break;
		}
		
        gameFont.draw(batch, "TAP ANYWHERE TO BEGIN", (Gdx.graphics.getWidth() - gameFont.getBounds("TAP ANYWHERE TO BEGIN").width) / 2,
        		Gdx.graphics.getHeight()/2 + gameFont.getBounds("TAP ANYWHERE TO BEGIN").height);
        batch.end();
	}
}
