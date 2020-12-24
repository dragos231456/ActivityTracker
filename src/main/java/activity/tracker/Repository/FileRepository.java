package activity.tracker.Repository;

import activity.tracker.Model.Activities.Activity;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository("fileRepo")
public final class FileRepository implements IRepository {
    private final String filepath = "repo.txt";
    private List<Activity> activities;

    public FileRepository() throws IOException, ClassNotFoundException {
        activities = readAllFromFile();
    }

    @Override
    public void resetFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filepath);
        writer.println("");
        writer.close();
    }

    @Override
    public List<Activity> readAllFromFile() throws IOException, ClassNotFoundException {
        activities = new ArrayList<>();
        ObjectInputStream fileReader;
        try {
            fileReader = new ObjectInputStream(new FileInputStream(filepath));
        }
        catch (EOFException ex) {
            return activities;
        }
        while(true) {
            try {
                activities.add((Activity) fileReader.readObject());
            }
            catch (IOException ex) {
                fileReader.close();
                return activities;
            }
        }
    }

    @Override
    public void writeAllToFile(List<Activity> activities) throws IOException {
        ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(filepath));
        for(Activity activity : activities) {
            fileWriter.writeObject(activity);
        }
        fileWriter.close();
    }

    @Override
    public Activity addActivity(Activity newActivity) throws RuntimeException, IOException {
        int index = 0;

        for(Activity activity : activities) {
            if(activity.equals(newActivity))
                throw new RuntimeException(AlreadyRegisteredMessage);

            if(activity.before(newActivity))
                index += 1;
        }

        activities.add(index,newActivity);
        writeAllToFile(activities);
        return newActivity;
    }

    @Override
    public Activity removeActivity(Activity newActivity) throws RuntimeException, IOException {
        Activity existingActivity = null;

        for(Activity activity : activities) {
            if(activity.equals(newActivity)) {
                existingActivity = activity;
            }
        }

        if(existingActivity == null)
            throw new RuntimeException(NotRegisteredMessage);

        activities.remove(existingActivity);
        writeAllToFile(activities);
        return existingActivity;
    }

    @Override
    public Activity updateActivity(Activity newActivity) throws RuntimeException, IOException {
        Activity existingActivity = null;

        for(Activity activity : activities) {
            if(activity.equals(newActivity)) {
                existingActivity = activity.deepcopy();
            }
        }

        if(existingActivity == null)
            throw new RuntimeException(NotRegisteredMessage);

        removeActivity(existingActivity);
        addActivity(newActivity);
        writeAllToFile(activities);
        return existingActivity;
    }

    @Override
    public Activity searchActivity(Activity newActivity) throws RuntimeException {
        for(Activity activity : activities) {
            if(activity.equals(newActivity))
                return activity;
        }
        throw new RuntimeException(NotRegisteredMessage);
    }

    @Override
    public Activity getActivityByIndex(int index) throws RuntimeException {
        return activities.get(index);
    }

    @Override
    public boolean exists(Activity newActivity) {
        for(Activity activity : activities) {
            if(activity.equals(newActivity))
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return activities.size();
    }

    @Override
    public List<Activity> getAllActivities() {
        return activities;
    }
}
