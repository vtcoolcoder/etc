package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;


public class ColorsObserver extends AbstractObserver {

    public ColorsObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
    
    }
}