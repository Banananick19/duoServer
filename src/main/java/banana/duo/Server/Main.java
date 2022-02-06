package banana.duo.Server;

// Отвечает за обмен сообщениями и их обработкой. Приложение запускается через него.

import banana.duo.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] parameters) throws IOException, InterruptedException {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a type(WIFI or BLUETOOTH): ");
        String type = reader.next(); // Scans the next token of the input as an int.
        reader.close();
        Server server = null;
        switch (type) {
            case "WIFI":
                server = new ServerWiFi(0, 80);
                server.startServer();
            case "BLUETOOTH":
                server = new ServerBluetooth();
                server.startServer();
        }
    }

}
