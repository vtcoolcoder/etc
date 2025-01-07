package dumbell.observers;


import dumbell.events.MainEvent;
import dumbell.publishers.AbstractPublisher;
import dumbell.constants.Render;


public class PrintingObserver extends AcceptedMainEventObserver implements Render {

    public PrintingObserver(AbstractPublisher... publishers) {
        super(publishers);
    }
    
       
    @Override
    public void processEvent(MainEvent event) {
    
    }
}