package observers;


import java.util.List;
import java.util.ArrayList;


public abstract class AbstractPublisher implements Subject {
    private final List<Observer> observers;
    protected Event event;
    
    
    protected AbstractPublisher() {
        observers = new ArrayList<>();
    }
    
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    
    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }
    
    
    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.accept(event));      
    }
}