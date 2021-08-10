package banana.duo.tasks;

import banana.duo.common.MessageType;

import java.awt.*;

public class TaskFactory {
    public static Task getTask(MessageType messageType) {
        switch (messageType) {
            case MouseMove:
                return new MouseMoveTask();
            case MouseClick:
                return new MouseClickTask();
        }
        return null; // TODO: ошибку мб?
    }
}
