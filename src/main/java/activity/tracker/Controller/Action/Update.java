package activity.tracker.Controller.Action;

import activity.tracker.Model.Activities.Activity;
import activity.tracker.Repository.IRepository;

import java.io.IOException;

public final class Update implements Action{
    private final IRepository repository;
    private final Activity oldActivity,newActivity;

    public Update(IRepository repository, Activity oldActivity, Activity newActivity) {
        this.repository = repository;
        this.oldActivity = oldActivity;
        this.newActivity = newActivity;
    }

    @Override
    public Activity undo() throws IOException {
        return repository.updateActivity(oldActivity);
    }

    @Override
    public Activity redo() throws IOException {
        return repository.updateActivity(newActivity);
    }
}
