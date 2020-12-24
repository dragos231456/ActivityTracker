package activity.tracker.Model.Activities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Walking implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private double distance;
    private String[] people;

    public Walking setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Walking setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public Walking setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public String[] getPeople() {
        return people;
    }

    public Walking setPeople(String[] people) {
        this.people = people;
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
        return new Walking()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setDistance(distance)
                .setPeople(people);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return distance > 0;
    }
}
