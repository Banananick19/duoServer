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


    public void startServer() throws IOException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String line = getLineIn();
                    System.out.println(line);
                    Message message = gson.fromJson(line, Message.class);
                    System.out.println(message);
                    if (!runTask.containsKey(message.getMessageType())) {
                        Task task = TaskFactory.getTask(message.getMessageType());
                        runTask.put(message.getMessageType(), task);
                    }
                    runTask.get(message.getMessageType()).update(message.getContent());
                } catch (IOException ex) {
                    offServer();
                }
            }
        });
        thread.start();
    }

    abstract protected boolean hasConnection();
    abstract protected String getLineIn() throws IOException;
    abstract protected void sendMessage(Message message) throws IOException;
    abstract protected void offServer();
}