package banana.duo.tasks;

import banana.duo.common.MessageType;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

abstract public class Task {
    protected MessageType type;
    private boolean run;

    abstract public void execute(String messageContent) throws AWTException;

    public void update(Object arg) {
        try {
            execute((String) arg);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


    public MessageType getType() {
        return type;
    }

}
