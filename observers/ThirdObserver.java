package observers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ThirdObserver extends AbstractObserver {
    
    @Autowired   
    public ThirdObserver(Subject... subjects) {
        super(subjects);
    }
    
       
    @Override
    public void accept(Event event) {
        System.out.println("Processing event from the third observer...");
        System.out.println(event.id() * 3);
    }
}