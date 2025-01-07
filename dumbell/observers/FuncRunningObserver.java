package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;


public class FuncRunningObserver extends AbstractObserver {
    
    public FuncRunningObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
    
    }
}