package banana.duo.tasks;

import banana.duo.common.MessageType;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

abstract public class Task implements Observer {
    private MessageType type;
    private boolean run;

    abstract public void execute(String messageContent) throws AWTException;

    @Override
    public void update(Observable o, Object arg) {
        try {
            execute((String) arg);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


    public MessageType getType() {
        return type;
    }

    public static Task task(MessageType type) {
        switch (type) {
            case MouseMove:
                return new MouseMoveTask();
        }
        throw new IllegalArgumentException("Type " + type.toString() + " is illegal");
    }
}
