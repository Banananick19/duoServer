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

public class MouseMoveTask extends Task {
    private MessageType messageType = MessageType.MouseMove;
    private Stack<Point> coords;
    private Robot robot;
    public MouseMoveTask() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        coords = new Stack<>();

    }
    @Override
    public void execute(String messageContent) throws AWTException {

                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                // Имитация длительной работы1
                Point coord = coords.pop();
                robot.mouseMove((int)(mouseLocation.getX()+coord.getX()), (int)(mouseLocation.getY()+coord.getY()));
                System.out.println("Я буду работать в отдельном потоке, а не в главном.");

    }


    public void update(Observable o, Object messag) {
        Message message = Message.parseString((String) messag);
        int x = Integer.parseInt(message.getContent().substring(0, message.getContent().indexOf("|")));
        int y = Integer.parseInt(message.getContent().substring(message.getContent().indexOf("|") + 1));
        coords.push((Point) new Point(x, y));
        System.out.println("Updated");
        super.update(o, messag);
    }

    @Override
    public MessageType getType() {
        return messageType;
    }
}
