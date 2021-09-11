package banana.duo.Server;

// Отвечает за обмен сообщениями и их обработкой. Приложение запускается через него.

import banana.duo.SpringConfig;
import banana.duo.tasks.MouseMoveTask;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;

import java.awt.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] parameters) throws IOException, ExecutionException, InterruptedException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Server server = (Server) context.getBean("server");
        server.startServer();
        while (true) {
            Thread.sleep(20000);
        }


    }

}
