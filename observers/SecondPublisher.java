package observers;


import org.springframework.stereotype.Component;


@Component
public class SecondPublisher extends AbstractPublisher {
        
    public SecondPublisher() {
        event = new Event(69);
    }
}