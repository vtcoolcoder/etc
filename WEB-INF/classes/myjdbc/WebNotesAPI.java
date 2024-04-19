package myjdbc;


import java.util.Map;
import java.util.Set;


public interface WebNotesAPI {
    void createNote(final Note... notes); 
     
    Set<Note> getAllNotes();  
           
    Map<String, Set<Note>> getNotes(final String subject);   
        
    Map<String, Set<Note>> getNotes(final Set<String> subjects);  
    
    String getNoteContent(final int id); 
    
    Map<String, Integer> getNotesBySubjectAmount();
    
    Set<String> getAllSubjects();   
    
    int getAllSubjectsAmount();    
    
    int getAllNotesAmount();   
    
    void updateNote(final String content, final int id);
    
    void deleteNote(final int id);         
}