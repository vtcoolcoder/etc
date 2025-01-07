package dumbell.configs;


//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan
@PropertySource(value="dumbell/properties/*", encoding="UTF-8")
public class MainConfig {

}