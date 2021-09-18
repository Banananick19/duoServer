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


@Component
public class Server implements GsonUseable {
    private Socket clientSocket; //сокет для общения
    private ServerSocket serverSocket; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private final Map<ActionType, Task> runTask;
    private String connectionTool;


    public Server(@Value("0") int port, @Value("80") int backlog, @Value("wi-fi") String connectionTool) throws IOException {
        this.connectionTool = connectionTool;
        if (connectionTool.equals("wi-fi")) {
            String ip = InetAddress.getLocalHost().getHostAddress();
            serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(ip)); //
        }
        runTask = new HashMap<>();
        System.out.println(this.getAddress());
    }


    public void startServer() throws IOException {
        clientSocket = serverSocket.accept();
        in = new BufferedReader(getInputSteamReader());
        out = new BufferedWriter(getOutputSteamWriter());
        if (connectionTool.equals("wi-fi")) {
            System.out.println("Has connection: " + clientSocket.isConnected() + " with address" + clientSocket.getInetAddress());
        }
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String line = getLineIn();
                    if ((line == null) | (Objects.equals(line, ""))) {
                        if (!hasConnection()) {
                            offServer();
                            startServer();
                            break;
                        }
                        continue;
                    }
                    System.out.println(line);
                    Message message = gson.fromJson(line, Message.class);
                    System.out.println(message);
                    if (!runTask.containsKey(message.getMessageType())) {
                        Task task = TaskFactory.getTask(message.getMessageType());
                        runTask.put(message.getMessageType(), task);
                    }
                    runTask.get(message.getMessageType()).update(message.getContent());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private boolean hasConnection() {
        if (connectionTool.equals("wi-fi")) {
            return clientSocket.isConnected();
        }
        return false;
    }

    private InputStreamReader getInputSteamReader() throws IOException {
        if (connectionTool.equals("wi-fi")) {
            return new InputStreamReader(clientSocket.getInputStream());
        }
        return new InputStreamReader(clientSocket.getInputStream()); //default
    }

    private OutputStreamWriter getOutputSteamWriter() throws IOException {
        if (connectionTool.equals("wi-fi")) {
            return new OutputStreamWriter(clientSocket.getOutputStream());
        }
        return new OutputStreamWriter(clientSocket.getOutputStream()); // default
    }


    public String getConnectionTool() {
        return connectionTool;
    }

    public void setConnectionTool(String connectionTool) {
        this.connectionTool = connectionTool;
    }

    public String getAddress() {
        return serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort();
    }

    public String getLineIn() throws IOException {
        return in.readLine();
    }

    public void sendMessage(Message message) throws IOException {
        out.write(message.toString());
    }

    public void offServer() {
        in = null;
        out = null;
        clientSocket = null;
        serverSocket = null;
    }
}