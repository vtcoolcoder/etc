package myservlets;


import jakarta.servlet.http.HttpServletRequest;


public class APIServlet {
    
    private ServiceServlet service;
    
    
    public APIServlet(HttpServletRequest request) {
        service = new ServiceServlet(request);
    }
    
    
    public String showCheckboxesWithSubjects() {
        return service.getResultingContent(Mode.PRELUDE);
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