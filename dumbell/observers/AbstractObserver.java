package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;
import java.util.Arrays;


public abstract class AbstractObserver implements Observer<Event> {

    private final AbstractPublisher[] publishers;
    
    
    protected AbstractObserver(AbstractPublisher... publishers) {
        this.publishers = publishers;
        registerPublishers();
    }
    
    
    private void registerPublishers() {
        Arrays.stream(publishers)
              .forEach(publisher -> publisher.registerObserver(this));
    }


    @Override
    public abstract void accept(Event event);
}