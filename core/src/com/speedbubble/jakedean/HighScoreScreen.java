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
	private TextButton one, two, three, back;
	
	private int i;
	
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
		
		one = new TextButton("", skin, "blue");
		one.setSize(2*Gdx.graphics.getWidth()/7, 75);
		two = new TextButton("", skin, "green");
		two.setSize(2*Gdx.graphics.getWidth()/7, 75);
		three = new TextButton("", skin, "red");
		three.setSize(2*Gdx.graphics.getWidth()/7, 75);
		
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
		
		stage = new Stage();
		setStage();
		
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
			
			one.setText("FIFTY BUBBLE");
			one.getLabel().setFontScale(.65f);
			one.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.FIFTY;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			two.setText("PACER");
			two.getLabel().setFontScale(1);
			two.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.PACER;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			three.setText("TIMED");
			three.getLabel().setFontScale(1);
			three.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.TIMED;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
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
			
			one.setText("ARCADE");
			one.getLabel().setFontScale(1);
			one.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.ARCADE;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			two.setText("PACER");
			two.getLabel().setFontScale(1);
			two.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.PACER;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			three.setText("TIMED");
			three.getLabel().setFontScale(1);
			three.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.TIMED;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
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
			
			one.setText("ARCADE");
			one.getLabel().setFontScale(1);
			one.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.ARCADE;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			two.setText("FIFTY BUBBLE");
			two.getLabel().setFontScale(.65f);
			two.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.FIFTY;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			three.setText("TIMED");
			three.getLabel().setFontScale(1);
			three.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.TIMED;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
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
			
			one.setText("ARCADE");
			one.getLabel().setFontScale(1);
			one.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.ARCADE;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			two.setText("FIFTY BUBBLE");
			two.getLabel().setFontScale(.65f);
			two.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.FIFTY;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			three.setText("PACER");
			three.getLabel().setFontScale(1);
			three.addListener(new InputListener(){
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Assets.playSound(Assets.bubbleSound);
	            	mode = Mode.PACER;
	                return true;
	            }
	            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	            	setStage();
	            }
			});
			
			break;
		}
	}
	
	private void setStage(){
		Table t = new Table();
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
		t.add(one).width(one.getWidth()).height(one.getHeight()).space(20);
		t.add(two).width(two.getWidth()).height(two.getHeight()).space(20);
		t.add(three).width(three.getWidth()).height(three.getHeight()).space(20);
		
		stage.getRoot().clear();
		stage.addActor(back);
		stage.addActor(t);
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
		batch.draw(background, (Gdx.graphics.getWidth() - 1024)/2, (Gdx.graphics.getHeight() - 512)/2);
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

    }
}
