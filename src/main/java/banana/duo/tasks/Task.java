package banana.duo.tasks;

import banana.duo.common.ActionType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

abstract public class Task {
    protected ActionType type;
    private boolean run;

    abstract public void execute(Map<String, String> messageContent) throws AWTException;

    public void update(Map<String, String> arg) {
        try {
            execute(arg);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


    public ActionType getType() {
        return type;
    }

}
