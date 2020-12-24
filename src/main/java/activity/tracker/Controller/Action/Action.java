package activity.tracker.Controller.Action;


import activity.tracker.Model.Activities.Activity;

import java.io.IOException;

public interface Action {
    Activity undo() throws IOException;
    Activity redo() throws IOException;
}
