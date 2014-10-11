package com.speedbubble.jakedean.android;


import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.speedbubble.jakedean.ActionResolver;
import com.speedbubble.jakedean.Settings;
import com.speedbubble.jakedean.SpeedBubble;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
//google play stuff
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	
	private static final String AD_UNIT_ID_BANNER = "ca-app-pub-9764238737556480/5776124655";
	//google play
	private GameHelper gameHelper;
	
	protected AdView adView;
	protected View gameView;
	protected RelativeLayout layout;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		
		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);
		
		createAdView();
		createGameView(config);
		layout.addView(gameView);
		layout.addView(adView);
		
		setContentView(layout);
		startAdvertising();
		
		
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
			}
			gameHelper.setup(this);
	}
	
	private void createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID_BANNER);
//		adView.setId(12345); // this is an arbitrary id, allows for relative positioning in createGameView()
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		adView.setLayoutParams(params);
		adView.setBackgroundColor(Color.TRANSPARENT);
		}
	
	private void createGameView(AndroidApplicationConfiguration cfg) {
		gameView = initializeForView(new SpeedBubble(this), cfg);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//		params.addRule(RelativeLayout.BELOW, adView.getId());
		gameView.setLayoutParams(params);
		}
	
	private void startAdvertising() {
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}
	
	@Override
	public void showAds(boolean show){
		if(show){
			runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	            	if(!Settings.ADS_ON) {
	            		layout.addView(adView);
	            		Settings.ADS_ON = true;
	            	}
	            }
	        });
		}
		else{
			runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	            	if(Settings.ADS_ON) {
	            		layout.removeView(adView);
	            		Settings.ADS_ON = false;
	            	}
	            }
	        });
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) adView.resume();
		else createAdView();
	}
	@Override
	public void onPause() {
		super.onPause();
		if (adView != null) adView.pause();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) adView.destroy();
	}
	
	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
		if (adView != null) adView.resume();
		else createAdView();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
		if (adView != null) adView.pause();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}
	
	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} 
		catch (final Exception ex) {
		}
	}
	
	@Override
	public void submitScoreGPGS(long score, String leaderboardID) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), leaderboardID, score);
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS(String leaderboardID) {
		
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), leaderboardID), 100);
		}
		else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}
	
	@Override
	public void getAchievementsGPGS() {
		
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
		}
		else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}
	
	@Override
	public void onSignInFailed() {
	}
	
	@Override
	public void onSignInSucceeded() {
	}


	@Override
	public void submitScoreGPGS(int score, String leaderboardID) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), leaderboardID, score);
		
	}
	
	@Override
	public void kill(){
		this.exit();
	}
	
	@Override
	public void goToWeb(String URL){
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
	}
}
