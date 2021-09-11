package banana.duo.tasks;

import banana.duo.common.ActionType;

public class TaskFactory {
    public static Task getTask(ActionType actionType) {
        switch (actionType) {
            case MouseMove:
                return new MouseMoveTask();
            case MouseClick:
                return new MouseClickTask();
            case KeyPress:
                return new KeyPressTask();
        }
        return null;
    }
}
