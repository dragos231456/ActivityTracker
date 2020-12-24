package activity.tracker.Controller.Validator;

import activity.tracker.Model.Activities.Activity;

public final class Validator implements IValidator{
    public Validator() {
    }

    @Override
    public void valid(Activity activity) {
        if(!activity.isValid())
            throw new RuntimeException("NOT VALID");
    }
}
