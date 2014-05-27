package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
	private TextButton timed, fiftyBubble, arcade, pacer, highScores, credits, options, quit, playSounds, stopSounds, back;
	private Label sounds;
	private Group optionsMenu;
	private Skin skin;
	private TextureAtlas skinAtlas;
	private Texture background, tint;
	private Image tintImage;
	
	public MainMenuScreen(final SpeedBubble game){
				
		if(width>height){landscape=true;}
		else{landscape=false;}
		
		skinAtlas = new TextureAtlas(Gdx.files.internal("skin/skinAtlas.pack"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"), skinAtlas);
		loopingBubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubbles.mp3"));
		
		timed = new TextButton("TIMED", skin, "blue");
		timed.getLabel().setFontScale(1.3f);
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
		
		fiftyBubble = new TextButton("50\nBUBBLE\n", skin, "blue");
		fiftyBubble.getLabel().setFontScaleX(1.1f);
		fiftyBubble.getLabel().setFontScaleY(1.3f);
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
		arcade.getLabel().setFontScaleY(1.5f);
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
		
		highScores = new TextButton("HIGH\nSCORES", skin, "green");
		highScores.addListener(new InputListener(){
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
		pacer.getLabel().setFontScale(1.25f);
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
		credits.getLabel().setFontScaleY(1.5f);
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
		options.getLabel().setFontScaleY(1.5f);
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
		
		quit = new TextButton("EXIT\nGAME", skin, "red");
		quit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	dispose();
            	Gdx.app.exit();
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
		
		sounds = new Label("SOUND EFFECTS", skin, "defaultWhite");
		sounds.getColor().set(0, 0, 0, 1);
		
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
			playSounds.setSize(Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/2);
			playSounds.setPosition(camera.position.x - playSounds.getWidth()/2, camera.position.y - playSounds.getHeight()/2);
			stopSounds.setSize(playSounds.getWidth(), playSounds.getHeight());
			stopSounds.setPosition(playSounds.getX(), playSounds.getY());
			back.setSize(100, 50);
			back.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2, camera.position.y+Gdx.graphics.getHeight()*camera.zoom/2 - 50);
			
		}
		else{
			playSounds.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2);
			playSounds.setPosition(camera.position.x - playSounds.getWidth()/2, camera.position.y - playSounds.getHeight()/2);
			stopSounds.setSize(playSounds.getWidth(), playSounds.getHeight());
			stopSounds.setPosition(playSounds.getX(), playSounds.getY());
			back.setSize(100, 50);
			back.setPosition(camera.position.x - Gdx.graphics.getWidth()/2, camera.position.y+Gdx.graphics.getHeight() - 50);
		}
		sounds.setFontScale(1.5f);
		sounds.setPosition(camera.position.x - sounds.getPrefWidth()/2, playSounds.getY() + playSounds.getHeight() + sounds.getPrefHeight());
		
		tintImage.setSize(Gdx.graphics.getWidth()*camera.zoom, Gdx.graphics.getHeight()*camera.zoom);
		tintImage.setPosition(camera.position.x - Gdx.graphics.getWidth()*camera.zoom/2, camera.position.y - Gdx.graphics.getHeight()*camera.zoom/2);
		
		optionsMenu.addActor(tintImage);
		optionsMenu.addActor(playSounds);
		optionsMenu.addActor(stopSounds);
		optionsMenu.addActor(sounds);
		optionsMenu.addActor(back);
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
			table.add(timed).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(0);
			table.add(fiftyBubble).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
			table.add(arcade).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
			table.add(pacer).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
			table.row();
			table.add(highScores).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
			table.add(options).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
			table.add(credits).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
			table.add(quit).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
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
			table.add(timed).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(0);
			table.add(fiftyBubble).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
			table.row();
			table.add(arcade).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
			table.add(pacer).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
			table.row();
			table.add(highScores).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
			table.add(options).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
			table.row();
			table.add(credits).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(0);
			table.add(quit).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
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
