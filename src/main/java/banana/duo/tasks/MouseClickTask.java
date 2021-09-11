package banana.duo.tasks;

import banana.duo.common.ActionType;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseClickTask extends Task {
    Robot robot;
    ActionType type = ActionType.MouseClick;

    public MouseClickTask() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(String messageContent) {
        switch (messageContent) {
            case "leftDown":
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                return;
            case "leftUp":
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                return;
            case "rightDown":
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                return;
            case "rightUp":
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                return;
        }
    }
}
