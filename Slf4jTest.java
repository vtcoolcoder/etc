//import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Slf4j(topic = "Slf4jTest")
public class Slf4jTest {

    private static final Logger log = LoggerFactory.getLogger("Slf4jTest"); 
    
       
    public static void main(String[] args) {
        log.info("Hello, World!");
    }
}