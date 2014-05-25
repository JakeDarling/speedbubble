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
    public static void writeHighScore(String name, float score) {
        hsHandle = Gdx.files.local(HIGHSCOREFILE);


        Array<Score> scores = fetchHighScores();

        scores.add(new Score(name, score));
        scores.sort(new ScoreComparator());

        if (scores.size > MAXHIGHSCORES) {
            scores.removeIndex(MAXHIGHSCORES);
        }

        for (Score s : scores) {
            hsHandle.writeString(s.toString(), true);
        }
    }

    public static Array<Score> fetchHighScores() {
        Array<Score> scores = new Array<Score>();
        hsHandle = Gdx.files.local(HIGHSCOREFILE);

        String text = hsHandle.readString();
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length - 1; i++) {
            String[] splits = lines[i].split(" ");
            scores.add(new Score(splits[0], Float.valueOf(splits[1])));
        }
        return scores;
    }

}
