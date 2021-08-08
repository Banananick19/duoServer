package banana.duo.Server;

import banana.duo.common.Message;
import banana.duo.common.MessageType;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.nio.channels.ServerSocketChannel;

public class Server extends Observable {
    private Socket clientSocket; //сокет для общения
    private ServerSocket server; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private HashMap<MessageType, Queue> messagesBuffer;
    private List<Observer> observers;


    public static void main(String[] parameters) throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        Server server = new Server(0, 80, InetAddress.getByName(ip));
        System.out.println(server.getAddress());
    }

    public Server(int port, int backlog, InetAddress bindAddress) throws IOException {

        observers = new ArrayList<>();
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
        server = new ServerSocket(port, backlog, bindAddress);
        System.out.println(this.getAddress());
        clientSocket = server.accept();
        messagesBuffer = new HashMap<>();
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
                if (!messagesBuffer.containsKey(message.getMessageType())) {
                    messagesBuffer.put(message.getMessageType(), new PriorityQueue<Message>());
                }
                System.out.println("Get MEssage");
                //messagesBuffer.get(message.getMessageType()).add(message); //TODO: память заполнится(сообщения не выгружаются из очереди)
                notifyObservers(message.toString());
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


    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(this, message);
        }
    }
}