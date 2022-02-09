package banana.duo.Server;

// Отвечает за обмен сообщениями и их обработкой. Приложение запускается через него.

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] parameters) throws IOException, InterruptedException {

          // Reading from System.in

        Scanner reader = new Scanner(System.in);
        Server server = null;
        Thread thread;
        while (true) {
            try {
                System.out.println("Enter a type(WIFI or BLUETOOTH): ");
                String type = reader.next(); // Scans the next token of the input as an int.
                switch (type) {
                    case "WIFI":
                        server = new ServerWiFi(0, 80);
                    case "BLUETOOTH":
                        server = new ServerBluetooth();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
                thread = server.startServer();
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }


    }
}
