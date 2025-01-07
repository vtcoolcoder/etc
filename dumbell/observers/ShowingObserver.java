package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;


public class ShowingObserver extends AbstractObserver {

    public ShowingObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
    
    }      
}