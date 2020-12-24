package activity.tracker.Model.Activities;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class OtherSports implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String name;
    private String location;

    public OtherSports setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public OtherSports setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public OtherSports setName(String name) {
        this.name = name;
        return this;
    }

    public OtherSports setLocation(String location) {
        this.location = location;
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


    public String getLocation() {
        return location;
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
        return new OtherSports()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setLocation(location)
                .setName(name);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return true;
    }
}
