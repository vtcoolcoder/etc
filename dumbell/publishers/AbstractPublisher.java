package dumbell.publishers;


import dumbell.observers.Observer;
import dumbell.observers.AbstractObserver;
import dumbell.events.Event;
import java.util.List;
import java.util.ArrayList;


public abstract class AbstractPublisher implements Publisher<Event> {

    private final List<AbstractObserver> observers;
    private final Event event;
    
    
    protected AbstractPublisher(Event event) {
        this.event = event;
        this.observers = new ArrayList<>();
    }


    @Override
    public void registerObserver(Observer<Event> observer) {
        observers.add((AbstractObserver) observer);
    }
    
    
    @Override
    public void removeObserver(Observer<Event> observer) {
        observers.remove(observer);
    }
  
    
    @Override
    public void notifyAllObservers() {
        observers.forEach(observer -> observer.accept(event));
    }
} 