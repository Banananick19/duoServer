package banana.duo.tasks;

import banana.duo.common.ActionType;

import java.awt.*;

abstract public class Task {
    protected ActionType type;
    private boolean run;

    abstract public void execute(String messageContent) throws AWTException;

    public void update(Object arg) {
        try {
            execute((String) arg);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


    public ActionType getType() {
        return type;
    }

}
