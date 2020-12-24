package activity.tracker.Model.Activities;

import activity.tracker.Model.DataStructures.TennisGame;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Tennis implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private TennisGame[] setsScores;
    private int breaks;
    private String pitch;
    private String against;

    public Tennis setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Tennis setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public TennisGame[] getSetsScores() {
        return setsScores;
    }

    public Tennis setSetsScores(TennisGame[] setsScores) {
        this.setsScores = setsScores;
        return this;
    }

    public int getBreaks() {
        return breaks;
    }

    public Tennis setBreaks(int breaks) {
        this.breaks = breaks;
        return this;
    }

    public String getPitch() {
        return pitch;
    }

    public Tennis setPitch(String pitch) {
        this.pitch = pitch;
        return this;
    }

    public String getAgainst() {
        return against;
    }

    public Tennis setAgainst(String against) {
        this.against = against;
        return this;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Duration getDuration() {
        return Duration.between(startTime,finishTime);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Activity) {
            return startTime.equals(((Activity) obj).getStartTime());
        }
        return false;
    }

    @Override
    public boolean before(Activity activity) {
        if(startTime.isEqual(activity.getStartTime())) {
            return finishTime.isBefore(activity.getFinishTime());
        }
        else
            return startTime.isBefore(activity.getStartTime());
    }

    @Override
    public Activity deepcopy() {
        return new Tennis()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setSetsScores(setsScores)
                .setAgainst(against)
                .setPitch(pitch)
                .setBreaks(breaks);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        for(TennisGame tg : setsScores) {
            if(!tg.isValid())
                return false;
        }
        return breaks >= 0;
    }
}
