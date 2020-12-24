package activity.tracker.Model.Activities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface Activity extends Serializable {
    LocalDateTime getStartTime();
    LocalDateTime getFinishTime();
    Duration getDuration();

    boolean before(Activity activity);
    Activity deepcopy();

    boolean isValid();
}
