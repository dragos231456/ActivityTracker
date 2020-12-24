package activity.tracker.Model.DataStructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class TennisGame {
    private int myScore;
    private int opScore;

    public int getMyScore() {
        return myScore;
    }

    public TennisGame setMyScore(int myScore) {
        this.myScore = myScore;
        return this;
    }

    public int getOpScore() {
        return opScore;
    }

    public TennisGame setOpScore(int opScore) {
        this.opScore = opScore;
        return this;
    }

    public boolean isValid() {
        return (myScore > 0 && opScore > 0);
    }
}
