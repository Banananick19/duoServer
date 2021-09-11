package banana.duo.tasks;

import banana.duo.common.ActionType;

import java.awt.*;


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
    public void execute(String messageContent) throws AWTException {
        int x = Integer.parseInt(messageContent.substring(0,messageContent.indexOf("|")));
        int y = Integer.parseInt(messageContent.substring(messageContent.indexOf("|") + 1));

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int)(mouseLocation.getX()+x), (int)(mouseLocation.getY()+y));

    }
}
