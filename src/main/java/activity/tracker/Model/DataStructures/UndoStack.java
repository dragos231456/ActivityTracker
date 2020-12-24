package activity.tracker.Model.DataStructures;
import activity.tracker.Controller.Action.Action;

import java.io.IOException;
import java.util.Stack;

public final class UndoStack implements IUndoStack{
    private final Stack<Action> undoStack,redoStack;

    public UndoStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    @Override
    public void addAction(Action a) {
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
        undoStack.push(a);
    }

    @Override
    public void undo() throws IOException {
        try {
            Action a = undoStack.pop();
            a.undo();
            redoStack.push(a);
        }
        catch (RuntimeException | IOException ex) {
            if(ex instanceof IOException)
                throw ex;
            throw new RuntimeException("No action to undo!");
        }
    }

    @Override
    public void redo() throws IOException {
        try {
            Action a = redoStack.pop();
            a.redo();
            undoStack.push(a);
        }
        catch (RuntimeException | IOException ex) {
            if(ex instanceof IOException)
                throw ex;
            throw new RuntimeException("No action to redo!");
        }
    }
}
