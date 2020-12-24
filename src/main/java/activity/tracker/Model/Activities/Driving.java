package activity.tracker.Model.Activities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Driving implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int distance;
    private int maxSpeed;

    public Driving setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Driving setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public int getDistance() {
        return distance;
    }

    public Driving setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public Driving setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
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
        return new Driving()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setDistance(distance)
                .setMaxSpeed(maxSpeed);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return (distance > 0 && maxSpeed > 0);
    }
}
