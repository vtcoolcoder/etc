package observers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SecondObserver extends AbstractObserver {
       
    @Autowired   
    public SecondObserver(Subject... subjects) {
        super(subjects);
    }
    
       
    @Override
    public void accept(Event event) {
        System.out.println("Processing event from the second observer...");
        System.out.println(event.id());
    }
}