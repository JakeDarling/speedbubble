package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class HighScores {
    public static final String TIMED = "timedScores.txt";
    public static final String ARCADE = "arcadeScores.txt";
    public static final String FIFTY = "fbScores.txt";
    public static final String PACER = "pacerScores.txt";
    
    private static final int MAXHIGHSCORES = 5;
    private static FileHandle hsHandle;
    
    
    /**
     * Function to compare the value of high scores
     */
    private static class ScoreComparator implements Comparator<Score> {
        public int compare(Score a, Score b) {
            if (a.getScore() < b.getScore())
                return -1;
            else if (a.getScore() > b.getScore())
                return 1;
            else return 0;
        }
    }
    
    
    /**
     * Function to create local high score files
     * if they don't already exist
     */
    public static void createLocalFiles(){
    	if(!Gdx.files.local(ARCADE).exists()){
    		try {
//    			Gdx.files.local(ARCADE).file().getParentFile().mkdirs();
				Gdx.files.local(ARCADE).file().createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(!Gdx.files.local(FIFTY).exists()){
    		try {
//    			Gdx.files.local(ARCADE).file().getParentFile().mkdirs();
				Gdx.files.local(FIFTY).file().createNewFile();
				Gdx.files.local(FIFTY).writeString("fake 99.999\n", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(!Gdx.files.local(PACER).exists()){
    		try {
//    			Gdx.files.local(ARCADE).file().getParentFile().mkdirs();
				Gdx.files.local(PACER).file().createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(!Gdx.files.local(TIMED).exists()){
    		try {
//    			Gdx.files.local(ARCADE).file().getParentFile().mkdirs();
				Gdx.files.local(TIMED).file().createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    /**
     * Function to write highscores to gdx local files
     * Sorting is done here along with brute attempt at insertion
     *
     * @param file - file name dependent upon the score.txt file you wish to access
     * @param name - name to display
     * @param score - score value (time(s), bubbles popped, etc)
     * @param desc - used along with the file name to determine what order to write the scores
     */
    public static void writeHighScore(String file, String name, float score, boolean desc) {
        hsHandle = Gdx.files.local(file);
        
        if(hsHandle.readString().equals("")){
        	
        	Score s = new Score(name, score);
        	hsHandle.writeString(s.toString()+"\n", false);
        }
        else{
        	Array<Score> scores = fetchHighScores(file, false);

	        // Add the score from the game that just ended
	        scores.add(new Score(name, score));
	        // Sort lowest[0] to highest[-1]
	        scores.sort(new ScoreComparator());
	        
	        // IF we want descending order (e.g. timed)
	        if (desc) scores.reverse();
	
	        if (scores.size > MAXHIGHSCORES) {
	            scores.removeIndex(MAXHIGHSCORES);
	        }
	        
	        hsHandle.writeString("", false);
	        
	        for (Score s : scores) {
	            hsHandle.writeString(s.toString() + "\n", true);
	        }
        }
    }

    /**
     * Read high scores from high score file
     * 
     * @param file - the score.txt you wish to access
     * 
     * @return high score list in ascending or descending order
     */
    public static Array<Score> fetchHighScores(String file, boolean desc) {
        Array<Score> scores = new Array<Score>();
        hsHandle = Gdx.files.local(file);
        
        if (hsHandle.readString().equals("")){
        	Score s = new Score("fake", 0);
        	scores.add(s);
        }
        else{
	        String text = hsHandle.readString();
	        String[] lines = text.split("\n");
	        for (int i = 0; i < lines.length; i++) {
	            String[] splits = lines[i].split(" ");
	            scores.add(new Score(splits[0], Float.valueOf(splits[1])));
	        }
        }

        if (desc) scores.reverse();

        return scores;
    }

}
