package activity.tracker.Model.Activities;

import activity.tracker.Model.DataStructures.Result;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Football implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int goalsScored;
    private int assists;
    private Result result;
    private String pitch;

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Football setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    @Override
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public Football setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public Football setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
        return this;
    }

    public int getAssists() {
        return assists;
    }

    public Football setAssists(int assists) {
        this.assists = assists;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public Football setResult(Result result) {
        this.result = result;
        return this;
    }

    public String getPitch() {
        return pitch;
    }

    public Football setPitch(String pitch) {
        this.pitch = pitch;
        return this;
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
        return new Football()
                .setAssists(assists)
                .setFinishTime(finishTime)
                .setGoalsScored(goalsScored)
                .setPitch(pitch)
                .setResult(result)
                .setStartTime(startTime);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return (goalsScored >= 0 && assists >=0);
    }
}
