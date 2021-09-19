package banana.duo.Server;

import banana.duo.common.ActionType;
import banana.duo.common.GsonUseable;
import banana.duo.common.Message;
import banana.duo.tasks.Task;
import banana.duo.tasks.TaskFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ServerWiFi extends Server implements GsonUseable {
    private Socket clientSocket; //сокет для общения
    private ServerSocket serverSocket; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет


    public ServerWiFi(@Value("0") int port, @Value("80") int backlog) throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(ip));
        System.out.println(this.getAddress());
    }


    public void startServer() throws IOException {
        clientSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        System.out.println("Has connection: " + clientSocket.isConnected() + " with address" + clientSocket.getInetAddress());
        super.startServer();
    }

    @Override
    protected boolean hasConnection() {
        return clientSocket.isConnected();
    }

    public String getAddress() {
        return serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort();
    }

    @Override
    public String getLineIn() throws IOException {
        return in.readLine();
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        out.write(message.toString());
    }

    @Override
    public void offServer() {
        in = null;
        out = null;
        clientSocket = null;
        serverSocket = null;
    }
}
