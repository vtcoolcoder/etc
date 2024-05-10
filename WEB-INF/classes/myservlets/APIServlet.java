package myservlets;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;


@Component
@Lazy
public class APIServlet {
    
    private final ServiceServlet service;
    
    
    @Autowired
    public APIServlet(ServiceServlet service) {
        this.service = service;
    }
    
    
    public String showCheckboxesWithSubjects() {
        return service.getResultingContent(Mode.PRELUDE);
    }
    
    
    public String showRandomNote() {
        return service.getResultingContent(Mode.RANDOM);
    }
    
    
    public String showNotesBySelectedSubjects() {
        return service.getResultingContent(Mode.READ);
    }
    
    
    public String showDefault() {
        return service.getResultingContent(Mode.DEFAULT);
    }
    
    
    public String showChangeSubject() {
        return service.getResultingContent(Mode.CHANGE_SUBJECT);
    }
    
    
    public String showChangeNote() {
        return service.getResultingContent(Mode.CHANGE_NOTE);
    }
    
    
    public String tryCreateNote() {
        return service.getResultingContent(Mode.CREATE);
    }
    
    
    public String showUpdateNote() {
        return service.getResultingContent(Mode.UPDATE);
    }
    
    
    public String showDeleteNote() {
        return service.getResultingContent(Mode.DELETE);
    }
}