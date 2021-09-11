package banana.duo.Server;

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

@Component
public class Server {
    private Socket clientSocket; //сокет для общения
    private ServerSocket server; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private Map<ActionType, Task> runTask;


    public Server(@Value("0") int port, @Value("80") int backlog) throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        runTask = new HashMap<>();
        server = new ServerSocket(port, backlog, InetAddress.getByName(ip)); //
        System.out.println(this.getAddress());
    }

    public void startServer() throws IOException {
        clientSocket = server.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        System.out.println("Has connection: " + hasConnection());
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String line = getLineIn();
                    if ((line == null) | (Objects.equals(line, ""))) {
                        continue;
                    }
                    System.out.println(line);
                    Message message = Message.parseString(line);
                    System.out.println(message);
                    System.out.println("Get MEssage");
                    if (!runTask.containsKey(message.getMessageType())) {
                        Task task = TaskFactory.getTask(message.getMessageType());
                        runTask.put(message.getMessageType(), task);
                    }
                    runTask.get(message.getMessageType()).update(message.getContent());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    continue;
                }
            }
        });
        thread.start();
    }

    public String getAddress() {
        return server.getInetAddress().getHostAddress() + ":" + server.getLocalPort();
    }

    public boolean hasConnection() {
        return clientSocket != null;
    }


    public String getLineIn() throws IOException {
        return in.readLine();
    }

    public void disconnectClient() throws IOException {
        clientSocket.close();
    }

    public void sendMessage(Message message) throws IOException {
        out.write(message.toString());
    }

    public void closeServer() throws IOException {
        server.close();
    }

}