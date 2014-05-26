package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class HighScores {
    private static final String HIGHSCOREFILE = "scores.txt";
    private static final int MAXHIGHSCORES = 10;
    private static FileHandle hsHandle;

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
     * Function to write highscores to gdx local files
     * Sorting is done here along with brute attempt at insertion
     *
     * @param name - nape to display
     * @param score - score value (time(s), bubbles popped, etc)
     */
    public static void writeHighScore(String name, float score, boolean desc) {
        hsHandle = Gdx.files.local(HIGHSCOREFILE);


        Array<Score> scores = fetchHighScores(false);

        // Add the score from the game that just ended
        scores.add(new Score(name, score));
        // Sort lowest[0] to highest[-1]
        scores.sort(new ScoreComparator());

        if (scores.size > MAXHIGHSCORES) {
            scores.removeIndex(MAXHIGHSCORES);
        }

        // IF we want descending order (e.g. timed)
        if (desc) scores.reverse();

        for (Score s : scores) {
            hsHandle.writeString(s.toString(), true);
        }
    }

    /**
     * Read high scores from high score file
     * @return high score list in ascending or descending order
     */
    public static Array<Score> fetchHighScores(boolean desc) {
        Array<Score> scores = new Array<Score>();
        hsHandle = Gdx.files.local(HIGHSCOREFILE);

        String text = hsHandle.readString();
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length - 1; i++) {
            String[] splits = lines[i].split(" ");
            scores.add(new Score(splits[0], Float.valueOf(splits[1])));
        }

        if (desc) scores.reverse();

        return scores;
    }

}
