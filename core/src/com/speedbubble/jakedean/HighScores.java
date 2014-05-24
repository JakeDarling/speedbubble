package com.speedbubble.jakedean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.Serializable;

public class HighScores implements Serializable {
    private static final String HIGHSCOREFILE = "scores.txt";
    private static FileHandle hsHandle;

    public static void writeHighScore(String name, int score) {

    }

    public static Array<Score> fetchHighScores() {
        Array<Score> scores = new Array<Score>();
        hsHandle = Gdx.files.local(HIGHSCOREFILE);

        return scores;
    }

}
