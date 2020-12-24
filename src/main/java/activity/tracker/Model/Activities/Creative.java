package activity.tracker.Model.Activities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Creative implements Activity {
    private LocalDateTime startTime, finishTime;
    private String description;

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Creative setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    @Override
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public Creative setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Creative setDescription(String description) {
        this.description = description;
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
        return new Creative()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setDescription(description);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return true;
    }
}
