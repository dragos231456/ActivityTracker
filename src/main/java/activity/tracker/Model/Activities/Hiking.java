package activity.tracker.Model.Activities;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Hiking implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String[] people;
    private String location;
    private double distance;
    private double elevation;

    public Hiking setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Hiking setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String[] getPeople() {
        return people;
    }

    public Hiking setPeople(String[] people) {
        this.people = people;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Hiking setLocation(String location) {
        this.location = location;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public Hiking setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public double getElevation() {
        return elevation;
    }

    public Hiking setElevation(double elevation) {
        this.elevation = elevation;
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
        return new Hiking()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setDistance(distance)
                .setElevation(elevation)
                .setLocation(location)
                .setPeople(people);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return distance > 0;
    }
}
