package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;


public abstract class AcceptedEventObserver<T extends Event> extends AbstractObserver {

    protected AcceptedEventObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
        processEvent((T) event);
    }
    
    
    protected abstract void processEvent(T event);
}