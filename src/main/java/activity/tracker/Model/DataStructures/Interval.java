package activity.tracker.Model.DataStructures;

import java.time.LocalDateTime;

public class Interval {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    public Interval(LocalDateTime startTime, LocalDateTime finishTime) {
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Interval setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public Interval setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }
}
