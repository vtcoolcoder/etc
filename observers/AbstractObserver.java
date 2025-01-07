package observers;


import java.util.Arrays;


public abstract class AbstractObserver implements Observer {
    private Subject[] subjects;
    
    
    protected AbstractObserver(Subject... subjects) {
        this.subjects = subjects;
        Arrays.stream(subjects).forEach(subject -> subject.registerObserver(this));
    }
    
    
    @Override
    public abstract void accept(Event event);
}