package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;


public class ServiceObserver extends AbstractObserver {

    public ServiceObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
    
    }
}