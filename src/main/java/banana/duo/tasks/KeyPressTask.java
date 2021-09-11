package banana.duo.tasks;

import banana.duo.common.ActionType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

public class KeyPressTask extends Task {
    private Robot robot;
    private ActionType type = ActionType.KeyPress;
    public KeyPressTask() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void execute(String messageContent) throws AWTException {
        int code = Integer.parseInt(messageContent.substring(0,messageContent.indexOf("|")));
        String state = messageContent.substring(messageContent.indexOf("|") + 1);
        if (state.equals("down")) {
            robot.keyPress(code);
        }
        if (state.equals("up")) {
            robot.keyRelease(code);
        }
    }

    @Override
    public void update(Object arg) {
        super.update(arg);
    }

}
