package dumbell.observers;


import dumbell.events.Event;
import dumbell.publishers.AbstractPublisher;
import dumbell.constants.CLParamNames;
import dumbell.constants.DefaultGripData;


public class PredicateObserver extends AbstractObserver 
        implements CLParamNames, DefaultGripData {
    
    public PredicateObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
    
    @Override
    public void accept(Event event) {
    
    }
}