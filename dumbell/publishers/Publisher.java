package dumbell.publishers;


import dumbell.observers.Observer;


public interface Publisher<T> {

    void registerObserver(Observer<T> observer);
    
    void removeObserver(Observer<T> observer);
    
    void notifyAllObservers();
}