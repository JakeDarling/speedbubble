package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen{
	
	private GameModeEnum selection;
	
	private int width=1160, height=660;
	private boolean landscape = false;
	
	/** STUFF FOR TABLE */
	int rows, cols, buttonWidth, buttonHeight; 
	float spacingW, spacingH;
	
	private Stage stage;
	private Viewport v;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Sound loopingBubbleSound;
	
/** all menu stuff */
	private Table table;
	private TextButton timed, fiftyBubble, arcade, pacer, highScoresOn, highScoresOff, credits, options, leadersOn, leadersOff, 
							playSounds, stopSounds, back, blue, yellow, orange, red, pink, purple;
	private TextButton[] allColors;
	private Label sounds, color;
	private Group optionsMenu;
	private Skin skin;
	private TextureAtlas skinAtlas;
	private Texture background;
	private Image tintImage;
	
	public MainMenuScreen(final SpeedBubble game){
		
		Assets.load();
				
		if(width>height){landscape=true;}
		else{landscape=false;}
		
		skinAtlas = new TextureAtlas(Gdx.files.internal("skin/newButtons.pack"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"), skinAtlas);
		loopingBubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubbles.mp3"));
		
		timed = new TextButton("TIMED", skin, "blue");
		timed.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	selection = GameModeEnum.TIMED;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, selection));
            }
		});
		
		fiftyBubble = new TextButton("SPEED\nBUBBLE\n", skin, "blue");
		fiftyBubble.getLabel();
		fiftyBubble.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	selection = GameModeEnum.FIFTY_BUBBLE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, selection));
            }
		});
		
		arcade = new TextButton("ARCADE", skin, "blue");
		arcade.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	selection = GameModeEnum.ARCADE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, selection));
            }
		});
		
		highScoresOn = new TextButton("GLOBAL\nHIGH\nSCORES", skin, "green");
		highScoresOn.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new HighScoreScreen(game));
            }
		});
		highScoresOff = new TextButton("LOCAL\nHIGH\nSCORES", skin, "red");
		highScoresOff.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new HighScoreScreen(game));
            }
		});
		
		pacer = new TextButton("PACER", skin, "blue");
		pacer.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	selection = GameModeEnum.PACER;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, selection));
            }
		});
		
		credits = new TextButton("CREDITS", skin, "green");
		credits.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	game.getScreen().dispose();
            	game.setScreen(new CreditScreen(game));
            }
		});
		
		options = new TextButton("OPTIONS", skin, "green");
		options.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	createOptionsMenu();
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	stage.getRoot().clear();
            	stage.addActor(optionsMenu);
            }
		});
		
		leadersOn = new TextButton("ONLINE\nLEADERS\nENABLED", skin, "green");
		leadersOn.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	Settings.leaderboardsEnabled = false;
            	setTable(table);
            }
		});
		
		leadersOff = new TextButton("ONLINE\nLEADERS\nDISABLED", skin, "red");
		leadersOff.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	Settings.leaderboardsEnabled = true;
            	setTable(table);
            }
		});
		
		playSounds = new TextButton("", skin, "play");
		playSounds.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Settings.soundEnabled = true;
            	loopingBubbleSound.loop();
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	updateOptionsMenu();
            }
		});
		
		stopSounds = new TextButton("", skin, "stop");
		stopSounds.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Settings.soundEnabled = false;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	loopingBubbleSound.stop();
            	updateOptionsMenu();
            }
		});
		
		back = new TextButton("BACK", skin, "red");
		back.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	stage.getRoot().clear();
            	stage.addActor(table);
            }
		});
		
		allColors = new TextButton[6];
		
		blue = new TextButton("", skin, "blueToggle");
		blue.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	for (int i = 0; i<6; i++){
            		if(allColors[i].isChecked() && !(i==Assets.BLUE)) allColors[i].setChecked(false);
            	}
            	Settings.favoriteColor = Assets.BLUE;
            	Assets.setBubbleColor(Settings.favoriteColor);
            }
		});
		
		yellow = new TextButton("", skin, "yellowToggle");
		yellow.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	for (int i = 0; i<6; i++){
            		if(allColors[i].isChecked() && !(i==Assets.YELLOW)) allColors[i].setChecked(false);
            	}
            	Settings.favoriteColor = Assets.YELLOW;
            	Assets.setBubbleColor(Settings.favoriteColor);
            }
		});
		
		orange = new TextButton("", skin, "orangeToggle");
		orange.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	for (int i = 0; i<6; i++){
            		if(allColors[i].isChecked() && !(i==Assets.ORANGE)) allColors[i].setChecked(false);
            	}
            	Settings.favoriteColor = Assets.ORANGE;
            	Assets.setBubbleColor(Settings.favoriteColor);
            }
		});
		
		red = new TextButton("", skin, "redToggle");
		red.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	for (int i = 0; i<6; i++){
            		if(allColors[i].isChecked() && !(i==Assets.RED)) allColors[i].setChecked(false);
            	}
            	Settings.favoriteColor = Assets.RED;
            	Assets.setBubbleColor(Settings.favoriteColor);
            }
		});
		
		pink = new TextButton("", skin, "pinkToggle");
		pink.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	for (int i = 0; i<6; i++){
            		if(allColors[i].isChecked() && !(i==Assets.PINK)) allColors[i].setChecked(false);
            	}
            	Settings.favoriteColor = Assets.PINK;
            	Assets.setBubbleColor(Settings.favoriteColor);
            }
		});
		
		purple = new TextButton("", skin, "purpleToggle");
		purple.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	for (int i = 0; i<6; i++){
            		if(allColors[i].isChecked() && !(i==Assets.PURPLE)) allColors[i].setChecked(false);
            	}
            	Settings.favoriteColor = Assets.PURPLE;
            	Assets.setBubbleColor(Settings.favoriteColor);
            }
		});
		
		allColors[0] = blue;
		allColors[1] = yellow;
		allColors[2] = orange;
		allColors[3] = red;
		allColors[4] = pink;
		allColors[5] = purple;
		
		sounds = new Label("SOUND EFFECTS", skin, "defaultWhite");
		sounds.getColor().set(0, 0, 0, 1);
		color = new Label("FAVORITE BUBBLE COLOR", skin, "defaultWhite");
		color.getColor().set(0, 0, 0, 1);
		
		optionsMenu = new Group();
		
		background = new Texture(Gdx.files.internal("mainMenuBackground.png"));
		tintImage = new Image (new Texture(Gdx.files.internal("tint.png")));
		
		
		camera = new OrthographicCamera();
		v = new ScreenViewport(camera);
		stage = new Stage(v);
		camera.zoom = 1.000000f;
		
		table = new Table();
		setTable(table);
		
		batch = new SpriteBatch();
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		
		Assets.playLoopingSound(loopingBubbleSound);
	}
	
	private void updateOptionsMenu(){
		if(Settings.soundEnabled){
			playSounds.setVisible(false);
			stopSounds.setVisible(true);
		}
		if(!Settings.soundEnabled){
			playSounds.setVisible(true);
			stopSounds.setVisible(false);
		}
	}
	
	private void createOptionsMenu(){
		updateOptionsMenu();
		if(landscape){
			playSounds.setSize(Gdx.graphics.getHeight()/4, Gdx.graphics.getHeight()/4);
			playSounds.setPosition(camera.position.x - playSounds.getWidth()/2, camera.position.y + playSounds.getHeight()*camera.zoom/2);
			stopSounds.setSize(playSounds.getWidth(), playSounds.getHeight());
			stopSounds.setPosition(playSounds.getX(), playSounds.getY());
			back.setSize(175, 90);
			back.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2, camera.position.y+Gdx.graphics.getHeight()*camera.zoom/2 - 90);
			float xSpacing = Gdx.graphics.getWidth()/49;
			blue.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getWidth()/7);
			blue.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2 + xSpacing, camera.position.y - blue.getHeight()*camera.zoom);
			yellow.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getWidth()/7);
			yellow.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2 + 2*xSpacing + blue.getWidth(), camera.position.y - blue.getHeight()*camera.zoom);
			orange.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getWidth()/7);
			orange.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2 + 3*xSpacing + 2*blue.getWidth(), camera.position.y - blue.getHeight()*camera.zoom);
			red.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getWidth()/7);
			red.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2 + 4*xSpacing + 3*blue.getWidth(), camera.position.y - blue.getHeight()*camera.zoom);
			pink.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getWidth()/7);
			pink.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2 + 5*xSpacing + 4*blue.getWidth(), camera.position.y - blue.getHeight()*camera.zoom);
			purple.setSize(Gdx.graphics.getWidth()/7, Gdx.graphics.getWidth()/7);
			purple.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2 + 6*xSpacing + 5*blue.getWidth(), camera.position.y - blue.getHeight()*camera.zoom);
		}
		else{
			playSounds.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2);
			playSounds.setPosition(camera.position.x - playSounds.getWidth()/2, camera.position.y - playSounds.getHeight()/2);
			stopSounds.setSize(playSounds.getWidth(), playSounds.getHeight());
			stopSounds.setPosition(playSounds.getX(), playSounds.getY());
			back.setSize(175, 90);
			back.setPosition(camera.position.x - Gdx.graphics.getWidth()/2, camera.position.y+Gdx.graphics.getHeight() - 90);
		}
		sounds.setFontScale(1.5f);
		sounds.setPosition(camera.position.x - sounds.getPrefWidth()/2, (playSounds.getY() + playSounds.getHeight() + sounds.getPrefHeight()/2)*camera.zoom);
		color.setFontScale(1.5f);
		color.setPosition(camera.position.x - color.getPrefWidth()/2, (blue.getY() + blue.getHeight() + color.getPrefHeight()/2)*camera.zoom);
		
		tintImage.setSize(Gdx.graphics.getWidth()*camera.zoom, Gdx.graphics.getHeight()*camera.zoom);
		tintImage.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2, camera.position.y - Gdx.graphics.getHeight()*camera.zoom/2);
		
		optionsMenu.addActor(tintImage);
		optionsMenu.addActor(playSounds);
		optionsMenu.addActor(stopSounds);
		optionsMenu.addActor(sounds);
		optionsMenu.addActor(back);
		optionsMenu.addActor(blue);
		optionsMenu.addActor(yellow);
		optionsMenu.addActor(orange);
		optionsMenu.addActor(red);
		optionsMenu.addActor(pink);
		optionsMenu.addActor(purple);
		optionsMenu.addActor(color);
	}
	
	private void updateWindow(){
		
		if(landscape && table.getWidth() > v.getViewportWidth()){
			camera.zoom = table.getWidth()/(Gdx.graphics.getWidth());
		}
		else if(table.getHeight() > v.getViewportHeight()){
			camera.zoom = table.getHeight()/(Gdx.graphics.getHeight());
		}
		else{
			camera.zoom = 1;
		}
	}
	
	private void setTable(Table t){
		t.clear();
		if(landscape){
			rows = 2;
			cols = 4;
			buttonWidth = width/(cols+1);
			buttonHeight = height/(rows+1);
			spacingW = (width - buttonWidth*cols) / (cols + 1);
			spacingH = (height - buttonHeight*rows) / (rows + 1);
						
			table.setSize(width, height);
			table.setPosition(Gdx.graphics.getWidth() - table.getWidth()/2, Gdx.graphics.getHeight() - table.getHeight()/2 );
			table.setColor(1,1,1,1);
			table.add(fiftyBubble).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(0);
				arcade.getLabel().setFontScale(buttonWidth / arcade.getLabel().getStyle().font.getBounds("ARCADE").width - .3f);
			table.add(arcade).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
				fiftyBubble.getLabel().setFontScale(buttonWidth / fiftyBubble.getLabel().getStyle().font.getBounds("BUBBLE").width - .3f);
			table.add(timed).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
				timed.getLabel().setFontScale(buttonWidth / timed.getLabel().getStyle().font.getBounds("TIMED").width - .5f);
			table.add(pacer).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
				pacer.getLabel().setFontScale(buttonWidth / pacer.getLabel().getStyle().font.getBounds("PACER").width - .4f);
			table.row();
		if(Settings.leaderboardsEnabled){
			table.add(highScoresOn).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
				highScoresOn.getLabel().setFontScale(buttonWidth / highScoresOn.getLabel().getStyle().font.getBounds("SCORES").width - .3f);
			}
		if(!Settings.leaderboardsEnabled){
			table.add(highScoresOff).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
			highScoresOff.getLabel().setFontScale(buttonWidth / highScoresOff.getLabel().getStyle().font.getBounds("SCORES").width - .3f);
			}
			table.add(options).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
				options.getLabel().setFontScale(buttonWidth / options.getLabel().getStyle().font.getBounds("OPTIONS").width - .2f);
			table.add(credits).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
				credits.getLabel().setFontScale(buttonWidth / credits.getLabel().getStyle().font.getBounds("CREDITS").width - .2f);
		if(Settings.leaderboardsEnabled){
			table.add(leadersOn).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
				leadersOn.getLabel().setFontScale(buttonWidth / leadersOn.getLabel().getStyle().font.getBounds("ENABLED").width - .2f);
			}
		if(!Settings.leaderboardsEnabled)	{
			table.add(leadersOff).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
			leadersOff.getLabel().setFontScale(buttonWidth / leadersOff.getLabel().getStyle().font.getBounds("DISABLED").width - .1f);
			}
		}
		else{
			rows = 4;
			cols = 2;
			buttonWidth = height/(cols+1);
			buttonHeight = width/(rows+1);
			spacingW = (height - buttonWidth*cols) / (cols + 1);
			spacingH = (width - buttonHeight*rows) / (rows + 1);
			
			table.setSize(height, width);
			table.setPosition(Gdx.graphics.getWidth() - table.getWidth()/2, Gdx.graphics.getHeight() - table.getHeight()/2 );
			table.setColor(1,1,1,1);
			table.add(arcade).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(0);
				arcade.getLabel().setFontScale(buttonWidth / arcade.getLabel().getStyle().font.getBounds("ARCADE").width - .3f);
			table.add(fiftyBubble).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
				fiftyBubble.getLabel().setFontScale(buttonWidth / fiftyBubble.getLabel().getStyle().font.getBounds("BUBBLE").width - .3f);
			table.row();
			table.add(timed).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
				timed.getLabel().setFontScale(buttonWidth / timed.getLabel().getStyle().font.getBounds("TIMED").width - .5f);
			table.add(pacer).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
				pacer.getLabel().setFontScale(buttonWidth / pacer.getLabel().getStyle().font.getBounds("PACER").width - .4f);
			table.row();
		if(Settings.leaderboardsEnabled){
			table.add(highScoresOn).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
				highScoresOn.getLabel().setFontScale(buttonWidth / highScoresOn.getLabel().getStyle().font.getBounds("SCORES").width - .3f);
			}
		if(!Settings.leaderboardsEnabled){
			table.add(highScoresOff).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
			highScoresOff.getLabel().setFontScale(buttonWidth / highScoresOff.getLabel().getStyle().font.getBounds("SCORES").width - .3f);
			}
			table.add(options).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
				options.getLabel().setFontScale(buttonWidth / options.getLabel().getStyle().font.getBounds("OPTIONS").width - .2f);
			table.row();
			table.add(credits).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
				credits.getLabel().setFontScale(buttonWidth / credits.getLabel().getStyle().font.getBounds("CREDITS").width - .2f);
		if(Settings.leaderboardsEnabled){
			table.add(leadersOn).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
				leadersOn.getLabel().setFontScale(buttonWidth / leadersOn.getLabel().getStyle().font.getBounds("ENABLED").width - .7f);
			}
		if(!Settings.leaderboardsEnabled)	{
			table.add(leadersOff).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
			leadersOff.getLabel().setFontScale(buttonWidth / leadersOff.getLabel().getStyle().font.getBounds("DISABLED").width - .7f);
			}
		}
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateWindow();
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, camera.position.x - 1024, camera.position.y - 512);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if(Gdx.graphics.getWidth()>Gdx.graphics.getHeight()){landscape=true; v.setWorldSize(800,480);}
		else{landscape=false; v.setWorldSize(480, 800);}
		updateWindow();
		
		Assets.phantom.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
		Assets.bubble.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
        Assets.previousBubble.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
		
		v.update(width, height, false);
		setTable(table);
		camera.position.set(table.getX() + table.getWidth()/2 , table.getY() + table.getHeight()/2 , 0);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}
	
}
