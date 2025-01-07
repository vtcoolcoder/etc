package observers;


import org.springframework.stereotype.Component;


@Component
public class ThirdPublisher extends AbstractPublisher {
        
    public ThirdPublisher() {
        event = new Event(47);
    }
}