package banana.duo.Server;

import banana.duo.common.GsonUseable;
import banana.duo.common.Message;
import banana.duo.common.ActionType;
import banana.duo.tasks.Task;
import banana.duo.tasks.TaskFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;



abstract public class Server implements GsonUseable {
    protected final Map<ActionType, Task> runTask = new HashMap<>();


    public Thread startServer() throws IOException, InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String line = getLineIn();
                    Message message = null;
                    System.out.println(line);
                    try{
                        message = gson.fromJson(line, Message.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    System.out.println(message.getMessageType());
                    if (!runTask.containsKey(message.getMessageType())) {
                        System.out.println("Должно работать");
                        Task task = TaskFactory.getTask(message.getMessageType());
                        runTask.put(message.getMessageType(), task);
                    }
                    runTask.get(message.getMessageType()).update(message.getContent());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    try {
                        offServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        });
        thread.start();
        return thread;
    }

    abstract protected boolean hasConnection();
    abstract protected void cleanIn() throws IOException;
    abstract protected String getLineIn() throws IOException;
    abstract protected void sendMessage(Message message) throws IOException;
    abstract protected void offServer() throws IOException;
}