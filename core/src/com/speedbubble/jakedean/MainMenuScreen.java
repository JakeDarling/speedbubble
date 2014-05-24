package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen{
	
	public enum Mode{
		TIMED, FIFTY_BUBBLE, ARCADE, ZEN, DEFAULT
	}
	
	private Mode mode;
	
	private int width=800, height=480;
	private boolean landscape = false;
	
	/** STUFF FOR TABLE */
	int rows, cols, buttonWidth, buttonHeight; 
	float spacingW, spacingH;
	
	private SpeedBubble game;
	private Stage stage;
	private Viewport v;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Sound buttonSound;
	
/** all menu stuff */
	private Table table, optionsMenu;
	private TextButton timed, fiftyBubble, arcade, zen, highScores, credits, options, quit;
	private Skin skin;
	private TextureAtlas skinAtlas;
	private Texture background;
	
	public MainMenuScreen(SpeedBubble sb){
				
		if(width>height){landscape=true;}
		else{landscape=false;}
		
		skinAtlas = new TextureAtlas(Gdx.files.internal("skin/skinAtlas.pack"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"), skinAtlas);
		buttonSound = Gdx.audio.newSound(Gdx.files.internal("bubbles.mp3"));
		
		mode = Mode.DEFAULT;
		
		timed = new TextButton("TIMED", skin, "blue");
		timed.getLabel().setFontScale(1.3f);
		timed.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
            	mode = Mode.TIMED;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            		game.getScreen().dispose();
            		game.setScreen(new GameScreen(game, mode));
            }
		});
		
		fiftyBubble = new TextButton("50\nBUBBLE\n", skin, "blue");
		fiftyBubble.getLabel().setFontScaleX(1.1f);
		fiftyBubble.getLabel().setFontScaleY(1.3f);
		fiftyBubble.getLabel();
		fiftyBubble.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
            	mode = Mode.FIFTY_BUBBLE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, mode));
            }
		});
		
		arcade = new TextButton("ARCADE", skin, "blue");
		arcade.getLabel().setFontScaleY(1.5f);
		arcade.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
            	mode = Mode.ARCADE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, mode));
            }
		});
		
		highScores = new TextButton("HIGH\nSCORES", skin, "green");
		highScores.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.getScreen().dispose();
            	game.setScreen(new HighScoreScreen(game));
            }
		});
		
		zen = new TextButton("ZEN", skin, "blue");
		zen.getLabel().setFontScale(1.5f);
		zen.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
            	mode = Mode.ZEN;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.getScreen().dispose();
            	game.setScreen(new GameScreen(game, mode));
            }
		});
		
		credits = new TextButton("CREDITS", skin, "green");
		credits.getLabel().setFontScaleY(1.5f);
		credits.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.getScreen().dispose();
            	game.setScreen(new CreditScreen(game));
            }
		});
		
		options = new TextButton("OPTIONS", skin, "green");
		options.getLabel().setFontScaleY(1.5f);
		options.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSound(buttonSound);
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
            	playSound(buttonSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.dispose();
            	Gdx.app.exit();
            }
		});
		
		background = new Texture(Gdx.files.internal("mainMenuBackground.png"));
		
		camera = new OrthographicCamera();
		v = new ScreenViewport(camera);
		stage = new Stage(v);
		camera.zoom = 1.000000f;
		
		table = new Table();
		setTable(table);
		
		batch = new SpriteBatch();
		game = sb;
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	private void playSound(Sound s){
		if (Settings.soundEnabled){
			s.play();
		}
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
			table.add(zen).width(buttonWidth).height(buttonHeight).padTop(0).padLeft(spacingW);
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
			table.add(zen).width(buttonWidth).height(buttonHeight).padTop(spacingH).padLeft(spacingW);
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
		batch.draw(background, Gdx.graphics.getWidth() - 1024, Gdx.graphics.getHeight() - 512);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if(Gdx.graphics.getWidth()>Gdx.graphics.getHeight()){landscape=true; v.setWorldSize(800,480);}
		else{landscape=false; v.setWorldSize(480, 800);}
		updateWindow();
		
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
