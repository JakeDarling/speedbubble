package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class HighScoreScreen implements Screen {
	
	private enum Mode{
		ARCADE, FIFTY, PACER, TIMED;
	}
	
	private Mode mode;
	
	private SpriteBatch batch;
	private Texture background, tint;
	private Stage stage;
	private Skin skin;
	private TextureAtlas skinAtlas;
	private Label title, first, firstName, firstScore, second, secondName, secondScore,
		third, thirdName, thirdScore, fourth, fourthName, fourthScore, fifth, fifthName, fifthScore;
	private Table table;
	private TextButton back, arcade, fifty, pacer, timed;
	
	private int i, spacing, buttonWidth;
	
	public HighScoreScreen (final SpeedBubble s){
		mode = Mode.ARCADE;
		
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("mainMenuBackground.png"));
		tint = new Texture(Gdx.files.internal("tint.png"));
		
		skinAtlas = new TextureAtlas(Gdx.files.internal("skin/skinAtlas.pack"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"), skinAtlas);
		
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
		
		spacing = Gdx.graphics.getWidth()/25;
		buttonWidth = Gdx.graphics.getWidth()/5;
		
		arcade = new TextButton("ARCADE", skin, "green");
		arcade.setSize(buttonWidth, 75);
		arcade.setPosition(spacing, 10);
		arcade.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = Mode.ARCADE;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            }
		});
		
		fifty = new TextButton("50 BUBBLE", skin, "green");
		fifty.setSize(buttonWidth, 75);
		fifty.setPosition(2*spacing+buttonWidth, 10);
		fifty.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = Mode.FIFTY;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            }
		});
		
		pacer = new TextButton("PACER", skin, "green");
		pacer.setSize(buttonWidth, 75);
		pacer.setPosition(3*spacing+2*buttonWidth, 10);
		pacer.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = Mode.PACER;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            }
		});
		
		timed = new TextButton("TIMED", skin, "green");
		timed.setSize(buttonWidth, 75);
		timed.setPosition(4*spacing+3*buttonWidth, 10);
		timed.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
            	mode = Mode.TIMED;
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	setTable(table);
            }
		});
		
		
		back = new TextButton("BACK", skin, "red");
		back.setSize(100, 50);
		back.setPosition(0, Gdx.graphics.getHeight() - 50);
		back.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	Assets.playSound(Assets.bubbleSound);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	stage.getRoot().clear();
            	s.setScreen(new MainMenuScreen(s));
            }
		});
		
		i = 0;
		
		table = new Table();
		setTable(table);
		
		stage = new Stage();
		stage.addActor(table);
		stage.addActor(back);
		stage.addActor(arcade);
		stage.addActor(fifty);
		stage.addActor(pacer);
		stage.addActor(timed);
		
		Gdx.input.setInputProcessor(stage);
		
	}

	private void prepareTable(){
		switch(mode){
		case ARCADE:
			title.setText("ARCADE");
			i = 0;
			while(i < HighScores.fetchHighScores(HighScores.ARCADE, false).size){
				if (i == 0){
					firstName.setText(HighScores.fetchHighScores(HighScores.ARCADE, false).get(i).getName());
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
			for (int j = i; j<5; j++){
				if (j == 1){
					secondName.setText("");
					secondScore.setText("");
				}
				else if (j == 2){
					thirdName.setText("");
					thirdScore.setText("");
				}
				else if (j == 3){
					fourthName.setText("");
					fourthScore.setText("");
				}
				else if (j == 4){
					fifthName.setText("");
					fifthScore.setText("");
				}
			}
						
			break;
		case FIFTY:
			title.setText("FIFTY BUBBLE");
			i = 0;
			while(i < HighScores.fetchHighScores(HighScores.FIFTY, false).size){
				if (i == 0){
					firstName.setText(HighScores.fetchHighScores(HighScores.FIFTY, false).get(i).getName());
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
			for (int j = i; j<5; j++){
				if (j == 1){
					secondName.setText("");
					secondScore.setText("");
				}
				else if (j == 2){
					thirdName.setText("");
					thirdScore.setText("");
				}
				else if (j == 3){
					fourthName.setText("");
					fourthScore.setText("");
				}
				else if (j == 4){
					fifthName.setText("");
					fifthScore.setText("");
				}
			}
			
			break;
		case PACER:
			title.setText("PACER");
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
			for (int j = i; j<5; j++){
				if (j == 1){
					secondName.setText("");
					secondScore.setText("");
				}
				else if (j == 2){
					thirdName.setText("");
					thirdScore.setText("");
				}
				else if (j == 3){
					fourthName.setText("");
					fourthScore.setText("");
				}
				else if (j == 4){
					fifthName.setText("");
					fifthScore.setText("");
				}
			}
			
			break;
		case TIMED:
			title.setText("TIMED");
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
			for (int j = i; j<5; j++){
				if (j == 1){
					secondName.setText("");
					secondScore.setText("");
				}
				else if (j == 2){
					thirdName.setText("");
					thirdScore.setText("");
				}
				else if (j == 3){
					fourthName.setText("");
					fourthScore.setText("");
				}
				else if (j == 4){
					fifthName.setText("");
					fifthScore.setText("");
				}
			}
			
			break;
		}
	}
	
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
	
	private String toText(float f){
		String s = new String();
		s = String.format("%.0f", f);
		return s;
	}
	
	private String toLongText(float f){
		String s = new String();
		s = String.format("%.3f", f);
		return s;
	}
	
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, (Gdx.graphics.getWidth() - 2048)/2, (Gdx.graphics.getHeight() - 1024)/2);
		batch.draw(tint, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		stage.draw();
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
    	Gdx.app.exit();
    }
}
