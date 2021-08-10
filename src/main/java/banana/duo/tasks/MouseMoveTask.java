package banana.duo.tasks;

import banana.duo.common.Message;
import banana.duo.common.MessageType;
import java.util.Observable;

import java.awt.*;
import java.net.InetAddress;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


//TODO: убрать говно отсюда(стак лишний)
public class MouseMoveTask extends Task {
    private final MessageType messageType = MessageType.MouseMove;
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
