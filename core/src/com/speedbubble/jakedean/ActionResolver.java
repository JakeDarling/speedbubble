package com.speedbubble.jakedean;

/** Action Resolver class to perform operations upon app launch, and to perform certain actions from inside the app that
 * 		need access to the UI thread
 * 
 * @author Dean
 *
 */
public interface ActionResolver {
	
	// Google play methods required to sign in to google and submit high scores to the world
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(long score, String leaderboardID);
	public void submitScoreGPGS(int score, String leaderboardID);
	public void unlockAchievementGPGS(String achievementId);
	public void getLeaderboardGPGS(String leaderboardID);
	public void getAchievementsGPGS();
	
	//Method that needs access to the UI thread in order to alter the screen layout (adding and removing advertisements)
	public void showAds(boolean show);
	
	//Method that needs permission to start a new Intent, opens the web browser and sends the user to a specified url
	public void goToWeb(String URL);
	
	//Method to terminate the app, necessary because I removed the binding on the Android back-button to customize it
	public void kill();
}
