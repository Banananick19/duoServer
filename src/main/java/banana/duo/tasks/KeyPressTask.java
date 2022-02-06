package banana.duo.tasks;

import banana.duo.common.ActionType;
import banana.duo.common.KeyCodes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

public class KeyPressTask extends Task {
    private Robot robot;
    private final ActionType type = ActionType.KeyPress;
    public KeyPressTask() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void execute(Map<String, String> messageContent) throws AWTException {
        int code = KeyCodes.keyCodes.get(messageContent.get("code"));
        String state = messageContent.get("state");
        if (state.equals("down")) {
            robot.keyPress(code);
        }
        if (state.equals("up")) {
            robot.keyRelease(code);
        }
        else {
            robot.keyPress(code);
            robot.keyRelease(code);
        }
    }

    @Override
    public void update(Map<String, String> arg) {
        super.update(arg);
    }

}
