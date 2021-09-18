package banana.duo.Server;

// Отвечает за обмен сообщениями и их обработкой. Приложение запускается через него.

import banana.duo.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] parameters) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Server server = (Server) context.getBean("server");
        server.startServer();
    }

}
