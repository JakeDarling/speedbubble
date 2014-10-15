package com.speedbubble.jakedean.desktop;

import com.badlogic.gdx.Gdx;
import com.speedbubble.jakedean.ActionResolver;

public class DesktopActionResolver implements ActionResolver{

	@Override
	public boolean getSignedInGPGS() {
		System.out.println("Returned whether signed in or not");
		return false;
	}

	@Override
	public void loginGPGS() {
		System.out.println("Tried to sign in to google play");
		
	}

	@Override
	public void submitScoreGPGS(long score, String leaderboardID) {
		System.out.println("Submit a timed score to a google leaderboard");
		
	}

	@Override
	public void submitScoreGPGS(int score, String leaderboardID) {
		System.out.println("Submit a integer score to a google leaderboard");
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		System.out.println("Unlocked an achievement");
	}

	@Override
	public void getLeaderboardGPGS(String leaderboardID) {
		System.out.println("Tried to display the google leaderboard");
		
	}

	@Override
	public void getAchievementsGPGS() {
		System.out.println("Tried to display an achievement");
		
	}

	@Override
	public void showAds(boolean show) {
		if(show)
			System.out.println("Displayed ads");
		else
			System.out.println("Hid ads");
		
	}

	@Override
	public void goToWeb(String URL) {
		Gdx.net.openURI(URL);
	}

	@Override
	public void kill() {
		System.out.println("Turned off game");
	}

}
