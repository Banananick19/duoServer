package banana.duo.Server;

import banana.duo.common.Message;
import banana.duo.common.MessageType;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.nio.channels.ServerSocketChannel;

public class Server {

    private Socket clientSocket; //сокет для общения
    private final ServerSocket server; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private HashMap<MessageType, Queue> messagesBuffer;


    public static void main(String[] parameters) throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        Server server = new Server(0, 80, InetAddress.getByName(ip));
        System.out.println(server.getAddress());
    }

    public Server(int port, int backlog, InetAddress bindAddress) throws IOException {
        server = new ServerSocket(port, backlog, bindAddress);
        System.out.println(this.getAddress());
        clientSocket = server.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        startServer();
    }

    private void startServer() {
        while (hasConnection()) {
            try {
                String line = getLineIn();
                if (line == null) {
                    continue;
                }
                Message message = Message.parseString(line);
                if (!messagesBuffer.containsKey(message.getMessageType())) {
                    messagesBuffer.put(message.getMessageType(), new PriorityQueue<Message>());
                }
                messagesBuffer.get(message.getMessageType()).add(message);
            } catch (IOException ex) {
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

    public Message getMessage(MessageType messageType) throws IOException {
        //TODO: сделать фильтр по типу.
        return (Message) messagesBuffer.get(messageType).remove();
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