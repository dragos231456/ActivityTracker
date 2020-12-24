package activity.tracker.Model.Activities;

import activity.tracker.Model.DataStructures.Result;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Volley implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int wonSets;
    private int lostSets;
    private int epicPlays;
    private Result result; //

    public Volley setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Volley setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public int getWonSets() {
        return wonSets;
    }

    public Volley setWonSets(int wonSets) {
        this.wonSets = wonSets;
        return this;
    }

    public int getLostSets() {
        return lostSets;
    }

    public Volley setLostSets(int lostSets) {
        this.lostSets = lostSets;
        return this;
    }

    public int getEpicPlays() {
        return epicPlays;
    }

    public Volley setEpicPlays(int epicPlays) {
        this.epicPlays = epicPlays;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public Volley setResult(Result result) {
        this.result = result;
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
        return new Volley()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setEpicPlays(epicPlays)
                .setResult(result)
                .setWonSets(wonSets)
                .setLostSets(lostSets);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return (wonSets >=0 && lostSets >=0 && epicPlays >= 0);
    }
}
