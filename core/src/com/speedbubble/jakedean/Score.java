package com.speedbubble.jakedean;

/** Score class
 * used by HighScores to write into the high score files and provides means of coupling someone's name to their score
 * 
 * @author Dean
 *
 */
public class Score{

    private String name;
    private float score;

    public Score(String name, float score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }

    // separated by a space because we break strings up by new lines and spaces
    public String toString() {
        return name + " " + score;
    }
}
