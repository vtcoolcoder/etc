package dumbell.observers;


import dumbell.events.Event;
import dumbell.events.MainEvent;
import dumbell.publishers.AbstractPublisher;


public abstract class AcceptedMainEventObserver extends AcceptedEventObserver<MainEvent> {

    protected AcceptedMainEventObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
     
    protected abstract void processEvent(MainEvent event);
}