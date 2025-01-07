package observers;


import org.springframework.stereotype.Component;


@Component
public class Publisher extends AbstractPublisher {
        
    public Publisher() {
        event = new Event(23);
    }
}