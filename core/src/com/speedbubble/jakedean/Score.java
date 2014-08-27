package com.speedbubble.jakedean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Score implements Serializable {

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

    public String toString() {
        return name + " " + score;
    }
}
