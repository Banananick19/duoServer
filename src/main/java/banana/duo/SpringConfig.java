package banana.duo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.util.ResourceBundle;

@Configuration
@ComponentScan("banana.duo.*")
public class SpringConfig {
    @Bean
    public ResourceBundle resourceBundle () {
        return ResourceBundle.getBundle("config");
    }
}
