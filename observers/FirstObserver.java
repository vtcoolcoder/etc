package observers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FirstObserver extends AbstractObserver {
    
    @Autowired   
    public FirstObserver(Subject... subjects) {
        super(subjects);
    }
    
       
    @Override
    public void accept(Event event) {
        System.out.println("Processing event from the first observer...");
        System.out.println(event);
    }
}
