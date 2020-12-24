package activity.tracker.Model.Activities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Cycling implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private double distance;
    private double averageSpeed;
    private double maxSpeed;
    private String route;

    public Cycling setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Cycling setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public Cycling setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public Cycling setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
        return this;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public Cycling setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
        return this;
    }

    public String getRoute() {
        return route;
    }

    public Cycling setRoute(String route) {
        this.route = route;
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
        return new Cycling()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setAverageSpeed(averageSpeed)
                .setDistance(distance)
                .setMaxSpeed(maxSpeed)
                .setRoute(route);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return (distance > 0 && maxSpeed > 0 && averageSpeed > 0);
    }
}
