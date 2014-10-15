package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/** Screen class for displaying both local and google high scores
 * 
 * @author Dean
 *
 */
public class HighScoreScreen implements Screen {
	
	private SpeedBubble game;
	
	// used to change between which leaderboard to show
	private GameModeEnum mode;
	
	// libgdx variables
	private SpriteBatch batch;
	private Texture background, tint;
	private Stage stage;
	private Skin skin;
	private TextureAtlas skinAtlas;
	private Label title, first, firstName, firstScore, second, secondName, secondScore,
		third, thirdName, thirdScore, fourth, fourthName, fourthScore, fifth, fifthName, fifthScore;
	private Table table;
	private TextButton back, arcade, fifty, pacer, timed;
	
	// used to determine where to place the buttons
	private int i, spacing, buttonWidth;
	
	public HighScoreScreen (SpeedBubble sb){
		
		this.game = sb;
		
		Gdx.input.setCatchBackKey(true);
		
		// since arcade mode is the main game mode, we initialize our leaderboard with arcade mode stats
		mode = GameModeEnum.ARCADE;
		
		// initialize libgdx variables
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("mainMenuBackground.png"));
		tint = new Texture(Gdx.files.internal("tint.png"));
		
		//used for my buttons, skin is connected to a json file and a file mapping out packaged images
		skinAtlas = new TextureAtlas(Gdx.files.internal("skin/newButtons.pack"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"), skinAtlas);
		
		// initializing all variables that will be used to display scores
		title = new Label("", skin, "defaultWhite");
		first = new Label("LEADER", skin, "defaultWhite");
		firstName = new Label("", skin, "defaultWhite");
		firstScore = new Label("", skin, "defaultWhite");
		second = new Label("SECOND", skin, "defaultWhite");
		secondName = new Label("", skin, "defaultWhite");
		secondScore = new Label("", skin, "defaultWhite");
		third = new Label("THIRD", skin, "defaultWhite");
		thirdName = new Label("", skin, "defaultWhite");
		thirdScore = new Label("", skin, "defaultWhite");
		fourth = new Label("FOURTH", skin, "defaultWhite");
		fourthName = new Label("", skin, "defaultWhite");
		fourthScore = new Label("", skin, "defaultWhite");
		fifth = new Label("FIFTH", skin, "defaultWhite");
		fifthName = new Label("", skin, "defaultWhite");
		fifthScore = new Label("", skin, "defaultWhite");
		
		// initializing the variables used to determine how the bubbles will be displayed
		spacing = Gdx.graphics.getWidth()/25;
		buttonWidth = Gdx.graphics.getWidth()/5;
		
		
	/**	All of these are my buttons used to cycle between the leaderboards
	 * 	if global leaderboards are disabled by the user, we will not prompt them to sign into google
	 */
		
		arcade = new TextButton("SPEED", skin, "green");
		arcade.setSize(buttonWidth, 75);
		arcade.setPosition(spacing, 10);
		arcade.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = GameModeEnum.ARCADE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            	if (Settings.leaderboardsEnabled){
            		game.actionResolver.getLeaderboardGPGS("CgkIh-6d6poMEAIQDA");
            	}
            }
		});
		
		fifty = new TextButton("RAPID 25", skin, "green");
		fifty.setSize(buttonWidth, 75);
		fifty.setPosition(2*spacing+buttonWidth, 10 );
		fifty.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = GameModeEnum.FIFTY_BUBBLE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            	if (Settings.leaderboardsEnabled){
            		game.actionResolver.getLeaderboardGPGS("CgkIh-6d6poMEAIQCw");
            	}
            }
		});
		
		pacer = new TextButton("PACER", skin, "green");
		pacer.setSize(buttonWidth, 75);
		pacer.setPosition(4*spacing+3*buttonWidth, 10);
		pacer.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = GameModeEnum.PACER;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            	if (Settings.leaderboardsEnabled){
            		game.actionResolver.getLeaderboardGPGS("CgkIh-6d6poMEAIQAw");
            	}
            }
		});
		
		timed = new TextButton("15 SEC", skin, "green");
		timed.setSize(buttonWidth, 75);
		timed.setPosition(3*spacing+2*buttonWidth, 10);
		timed.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = GameModeEnum.TIMED;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            	if (Settings.leaderboardsEnabled){
            		game.actionResolver.getLeaderboardGPGS("CgkIh-6d6poMEAIQBA");
            	}
            }
		});
		
		
		back = new TextButton("BACK", skin, "red");
		back.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/10);
		back.setPosition(0, Gdx.graphics.getHeight() - 2*back.getHeight());
		back.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	stage.getRoot().clear();
            	dispose();
            		game.setScreen(new MainMenuScreen(game));
            }
		});
		
		// initializing my iterator for getting high scores from the local high scores files
		i = 0;
		
		// initializing and setting the table that will contain the high scores
		table = new Table();
		setTable(table);
		
		// adding buttons to the stage (the stage is basically just an input processor that holds actors)
		stage = new Stage();
		stage.addActor(table);
		stage.addActor(back);
		stage.addActor(arcade);
		stage.addActor(fifty);
		stage.addActor(pacer);
		stage.addActor(timed);
		
		// set the input processor to the stage so the actors inside the stage are activated and waiting for user interaction
		Gdx.input.setInputProcessor(stage);
		
	}
	
	/** This method will check which mode we want to view the local high scores for
	 * 		-> grab the information from the high scores file
	 * 		-> set our labels, title = game mode title, the rest hold the names and scores of the top 5 LOCAL scores
	 * 		-> if no score was set, we give the name "not_set" and a score of either 0 or 99.99 (this is written in the high score file)
	 * 
	 *  This is just for the local scores. not global scores.
	 */
	private void prepareTable(){
		switch(mode){
		case ARCADE:
			// based on the selected mode we set the labels accordingly
			title.setText("SPEED BUBBLE - PERSONAL TOP 5");
			i = 0;
			
			// we fetch the high scores of the specified file (arcade),
			// this returns a string with 2 pieces of data separated by a space
			//		getName() returns what comes before the space, getScore() returns what comes after the space
			// set name to the first piece of data, set score to second piece of data
			while(i < HighScores.fetchHighScores(HighScores.ARCADE, false).size){
				if (i == 0){
					firstName.setText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getName());
					
					// the method "toText(float)" was created by me to return a string holding an integer with no decimal places
					firstScore.setText(toText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getScore()));
				}
				else if (i == 1){
					secondName.setText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getName());
					secondScore.setText(toText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getScore()));
				}
				else if (i == 2){
					thirdName.setText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getName());
					thirdScore.setText(toText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getScore()));
				}
				else if (i == 3){
					fourthName.setText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getName());
					fourthScore.setText(toText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getScore()));
				}
				else if (i == 4){
					fifthName.setText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getName());
					fifthScore.setText(toText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getScore()));
				}
				i++;
			}
			break;
			
		case FIFTY_BUBBLE:
			title.setText("RAPID 25 - PERSONAL TOP 5");
			i = 0;
			while(i < HighScores.fetchHighScores(HighScores.FIFTY, false).size){
				if (i == 0){
					firstName.setText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getName());
					
					// the method "toLongText(float) was created by me in order to return a string holding a number with 3 decimal places
					firstScore.setText(toLongText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getScore()));
				}
				else if (i == 1){
					secondName.setText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getName());
					secondScore.setText(toLongText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getScore()));
				}
				else if (i == 2){
					thirdName.setText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getName());
					thirdScore.setText(toLongText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getScore()));
				}
				else if (i == 3){
					fourthName.setText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getName());
					fourthScore.setText(toLongText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getScore()));
				}
				else if (i == 4){
					fifthName.setText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getName());
					fifthScore.setText(toLongText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getScore()));
				}
				i++;
			}
			break;
			
		case PACER:
			title.setText("PACER - PERSONAL TOP 5");
			i = 0;
			while(i < HighScores.fetchHighScores(HighScores.PACER, false).size){
				if (i == 0){
					firstName.setText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getName());
					firstScore.setText(toText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getScore()));
				}
				else if (i == 1){
					secondName.setText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getName());
					secondScore.setText(toText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getScore()));
				}
				else if (i == 2){
					thirdName.setText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getName());
					thirdScore.setText(toText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getScore()));
				}
				else if (i == 3){
					fourthName.setText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getName());
					fourthScore.setText(toText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getScore()));
				}
				else if (i == 4){
					fifthName.setText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getName());
					fifthScore.setText(toText(HighScores.fetchHighScores(HighScores.PACER, false).get(i).getScore()));
				}
				i++;
			}
			break;
			
		case TIMED:
			title.setText("15 SECOND RUSH - PERSONAL TOP 5");
			i = 0;
			while(i < HighScores.fetchHighScores(HighScores.TIMED, false).size){
				if (i == 0){
					firstName.setText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getName());
					firstScore.setText(toText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getScore()));
				}
				else if (i == 1){
					secondName.setText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getName());
					secondScore.setText(toText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getScore()));
				}
				else if (i == 2){
					thirdName.setText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getName());
					thirdScore.setText(toText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getScore()));
				}
				else if (i == 3){
					fourthName.setText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getName());
					fourthScore.setText(toText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getScore()));
				}
				else if (i == 4){
					fifthName.setText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getName());
					fifthScore.setText(toText(HighScores.fetchHighScores(HighScores.TIMED, false).get(i).getScore()));
				}
				i++;
			}
			break;
		}
	}
	
	/** This will erase all content from the table, update all of the labels based on which mode we select
	 *  Then it will add all of the labels containing score, name, and title to the table
	 * 
	 * @param t = the table to be modified
	 */
	private void setTable(Table t){
		t.reset();
		prepareTable();
		
		t.setSize(3*Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight());
		t.setPosition(Gdx.graphics.getWidth()/8, 0);
		t.setColor(1,1,1,1);
		t.add(title).colspan(3).center();
		t.row();
		t.add(first).center().padTop(Gdx.graphics.getHeight()/24);
		t.add(firstName).right().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.add(firstScore).left().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.row();
		t.add(second).center().padTop(Gdx.graphics.getHeight()/24);
		t.add(secondName).right().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.add(secondScore).left().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.row();
		t.add(third).center().padTop(Gdx.graphics.getHeight()/24);
		t.add(thirdName).right().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.add(thirdScore).left().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.row();
		t.add(fourth).center().padTop(Gdx.graphics.getHeight()/24);
		t.add(fourthName).right().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.add(fourthScore).left().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.row();
		t.add(fifth).center().padTop(Gdx.graphics.getHeight()/24);
		t.add(fifthName).right().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.add(fifthScore).left().padTop(Gdx.graphics.getHeight()/24).padLeft(Gdx.graphics.getWidth()/12);
		t.row();
	}
	
	/** When the high score files are created and updated, they are always passed a float for a "score"
	 *  This method is necessary to take that float, and transform it into a string with no decimal places
	 * 
	 */
	private String toText(float f){
		String s = new String();
		s = String.format("%.0f", f);
		return s;
	}
	
	/** When the high score files are created and updated, they are always passed a float for a "score"
	 *  This method is necessary to take that float, and transform it into a string with 3 decimal places
	 * 
	 */
	private String toLongText(float f){
		String s = new String();
		s = String.format("%.3f", f);
		return s;
	}
	
	// called every time a new frame is drawn to the screen
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// draw background
		batch.begin();
		batch.draw(background, (Gdx.graphics.getWidth() - 2048)/2, (Gdx.graphics.getHeight() - 1024)/2);
		batch.draw(tint, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		// draw foreground (everything that has been added to the stage will draw)
		stage.draw();
		
		//check if the Android back button has been tapped -> dispose the high score screen's objects and send user back to the main menu
		if (Gdx.input.isKeyPressed(Keys.BACK) && !Settings.MAIN_MENU){
			dispose();
        	game.setScreen(new MainMenuScreen(game));
		}
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
    	batch.dispose();
    	background.dispose();
    	tint.dispose();
    	stage.dispose();
    	skin.dispose();
    	skinAtlas.dispose();
    	
    }
}
