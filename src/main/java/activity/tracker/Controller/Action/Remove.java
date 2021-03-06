package activity.tracker.Controller.Action;

import activity.tracker.Model.Activities.Activity;
import activity.tracker.Repository.IRepository;

import java.io.IOException;

public final class Remove implements Action{
    private final IRepository repository;
    private final Activity activity;

    public Remove(IRepository repository, Activity activity) {
        this.repository = repository;
        this.activity = activity;
    }

    @Override
    public Activity undo() throws IOException {
        return repository.addActivity(activity);
    }

    @Override
    public Activity redo() throws IOException {
        return repository.removeActivity(activity);
    }
}
