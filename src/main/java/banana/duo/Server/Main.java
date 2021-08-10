package banana.duo.Server;

// Отвечает за обмен сообщениями и их обработкой. Приложение запускается через него.

import banana.duo.tasks.MouseMoveTask;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] parameters) throws IOException, ExecutionException, InterruptedException {
        String ip = InetAddress.getLocalHost().getHostAddress();

        CompletableFuture<Server> future = CompletableFuture.supplyAsync(new Supplier<Server>() {
            @Override
            public Server get() {
                try {
                    Server server = new Server(0, 80, InetAddress.getByName(ip));

                    return server;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        Server server = future.get();
        System.out.println("not blocking");

        while (true) {
            Thread.sleep(20000);
        }


    }

}
