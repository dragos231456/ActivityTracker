package activity.tracker.Model.DataStructures;
import activity.tracker.Controller.Action.Action;

import java.io.IOException;

public interface IUndoStack {
    void addAction(Action a);
    void undo() throws IOException;
    void redo() throws IOException;
}
