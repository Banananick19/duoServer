package banana.duo.Server;

import banana.duo.common.Message;
import banana.duo.common.MessageType;
import banana.duo.tasks.Task;
import banana.duo.tasks.TaskFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.nio.channels.ServerSocketChannel;


// TODO: оповещать только сообщения данного типа(иначе пизда)
public class Server {
    private Socket clientSocket; //сокет для общения
    private ServerSocket server; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private Map<MessageType, Task> runTask;


    public static void main(String[] parameters) throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        Server server = new Server(0, 80, InetAddress.getByName(ip));
        System.out.println(server.getAddress());
    }

    public Server(int port, int backlog, InetAddress bindAddress) throws IOException {
        Thread thread = new Thread(() -> {
            try {
                startServer(port, backlog, bindAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void startServer(int port, int backlog, InetAddress bindAddress) throws IOException {
        runTask = new HashMap<>();
        server = new ServerSocket(port, backlog, bindAddress);
        System.out.println(this.getAddress());
        clientSocket = server.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        System.out.println("Has connection: " + hasConnection());
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
    }

    public String getAddress() {
        return server.getInetAddress().getHostAddress() + ":" + server.getLocalPort();
    }

    public boolean hasConnection() {
        return clientSocket != null;
    }

    public boolean hasMessage(MessageType messageType) {
        return false;//TODO: Есть ли новое сообщение данного типа.
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