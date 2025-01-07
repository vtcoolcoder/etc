package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;


public class ValidatingObserver extends AbstractObserver {

    public ValidatingObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
    
    }      
}