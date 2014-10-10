package com.speedbubble.jakedean;

public interface ActionResolver {
	
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(long score, String leaderboardID);
	public void submitScoreGPGS(int score, String leaderboardID);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS(String leaderboardID);
	public void getAchievementsGPGS();
	
	public void kill();
	public void showAds(boolean show);
	
	public void goToWeb(String URL);
	
}
