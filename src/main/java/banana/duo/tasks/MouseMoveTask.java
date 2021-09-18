package banana.duo.tasks;

import banana.duo.common.ActionType;

import java.awt.*;
import java.util.Map;


public class MouseMoveTask extends Task {
    private final ActionType actionType = ActionType.MouseMove;
    private Robot robot;
    public MouseMoveTask() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void execute(Map<String, String> messageContent) throws AWTException {
        int x = Integer.parseInt(messageContent.get("x"));
        int y = Integer.parseInt(messageContent.get("y"));

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int)(mouseLocation.getX()+x), (int)(mouseLocation.getY()+y));

    }
}
