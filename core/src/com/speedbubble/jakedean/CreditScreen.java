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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/** Screen Class to display credits and offer a donate option to help support WTGS app developments
 * 
 * @author Dean
 *
 */
public class CreditScreen implements Screen {
	
	private SpeedBubble game;
	private Texture credits, tint;
	private SpriteBatch batch;
	
	private TextureAtlas skinAtlas;
	private Skin skin;
	private TextButton button;
	private Stage stage;
	
	public CreditScreen (SpeedBubble sb){
		
		Gdx.input.setCatchBackKey(true);
		
		game = sb;
		credits = new Texture(Gdx.files.internal("credits.png"));
		tint = new Texture(Gdx.files.internal("tint.png"));
		
		batch = new SpriteBatch();
		
		skinAtlas = new TextureAtlas(Gdx.files.internal("skin/newButtons.pack"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"), skinAtlas);
		
		/** creates my donation button, when clicked it will start a new Intent to go to the supplied URL
		 */
		button = new TextButton("DONATE!", skin, "green");
		button.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getWidth()/5);
		button.setPosition(0, (Gdx.graphics.getHeight() - button.getHeight())/2);
		button.getLabel().setFontScale(button.getWidth() / button.getLabel().getStyle().font.getBounds("DONATE!").width - .3f);
		button.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.actionResolver.goToWeb("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=BGPBE9F47Q7AU");
            }
		});
		
		stage = new Stage();
		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(tint, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(credits, Gdx.graphics.getWidth()/2 - 530*Gdx.graphics.getHeight()/480/2, 0, 530*Gdx.graphics.getHeight()/480, 9*Gdx.graphics.getHeight()/10);
		button.draw(batch, 1);
		batch.end();
		
		
		if (Gdx.input.isKeyPressed(Keys.BACK) && !Settings.MAIN_MENU){
        	game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
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
		batch.dispose();
	}

}
