package activity.tracker.Repository;

import activity.tracker.Model.Activities.Activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IRepository {
    String NotRegisteredMessage =  "There is no activity with this startTime registere!";
    String AlreadyRegisteredMessage = "There si already one activity with this startTime!";

    List<Activity> readAllFromFile() throws IOException, ClassNotFoundException;
    void writeAllToFile(List<Activity> activities) throws IOException;

    Activity addActivity(Activity newActivity) throws RuntimeException, IOException;
    Activity removeActivity(Activity newActivity) throws RuntimeException, IOException;
    Activity updateActivity(Activity newActivity) throws RuntimeException, IOException;
    Activity searchActivity(Activity newActivity) throws RuntimeException;
    Activity getActivityByIndex(int index) throws RuntimeException;

    boolean exists(Activity newActivity);
    int size();

    List<Activity> getAllActivities();

    void resetFile() throws FileNotFoundException;
}
