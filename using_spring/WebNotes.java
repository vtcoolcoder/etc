package using_spring;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Cleanup;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;


@Component
public final class WebNotes implements WebNotesAPI {

    private final NoteShower noteShower;
    
    
    public static void main(String[] args) {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        var webNotes = context.getBean(WebNotes.class);   
        
        selecting(webNotes);    
        
        creating(webNotes);
        selecting(webNotes);
        
        updating(webNotes);
        selecting(webNotes);
        
        deleting(webNotes);
        selecting(webNotes);
    }
    
    
    @Autowired
    public WebNotes(NoteShower noteShower) {
        this.noteShower = noteShower;
    }
    
    
    @Override
    public void createNote(final Note... notes) {
        for (Note note : notes) {
            noteShower.getAddNote().accept(
                    note.subject(), note.note());
        }
    }
    
    
    @Override 
    public Set<Note> getAllNotes() {
        return noteShower.getAllNotes().get();
    }
    
    
    @Override       
    public Map<String, Set<Note>> getNotes(final String subject) {
        return getAllNotes().stream()
                .filter(note -> subject.equals(note.subject()))
                .collect(groupingBy(
                        Note::subject, 
                        LinkedHashMap::new, 
                        toCollection(LinkedHashSet::new)));
    }
    
    
    @Override    
    public Map<String, Set<Note>> getNotes(final Set<String> subjects) {
        return getAllNotes().stream()
                .filter(note -> subjects.contains(note.subject()))
                .collect(groupingBy(
                        Note::subject, 
                        LinkedHashMap::new, 
                        toCollection(LinkedHashSet::new)));
    }  
   
   
    @Override
    public String getNoteContent(final int id) {
        return noteShower.getNoteById().apply(id);
    }
    
    
    @Override
    public Map<String, Integer> getNotesBySubjectAmount() {
        return noteShower.getNotesBySubjectAmount().get();
    }
    
    
    @Override
    public Set<String> getAllSubjects() {
        return noteShower.getDistinctSubjects().get();
    }
    
    
    @Override
    public int getAllSubjectsAmount() {
        return noteShower.getAllSubjectsAmount().get();
    }   
   
   
    @Override
    public int getAllNotesAmount() {
        return noteShower.getAllNotesAmount().get();
    } 
    
    
    @Override
    public Note getRandomNote() {
        return noteShower.getRandom().get();
    }
    
    
    @Override
    public void updateNote(final String content, final int id) {
        noteShower.getTransactionUpdate().accept(content, id);
    }
   
   
    @Override
    public void deleteNote(final int id) {
        noteShower.getTransactionDelete().accept(id);
    }
    
    
    private static void selecting(WebNotes webNotes) {
        webNotes.getAllSubjects().stream().forEach(System.out::println);
        System.out.println();
        
        System.out.printf("Всего тем: %d%nВсего заметок: %d%n",
                webNotes.getAllSubjectsAmount(),
                webNotes.getAllNotesAmount());
        System.out.println();
        
        /*
        System.out.println(webNotes.getNoteContent(7));
        System.out.println();
        */
        
        webNotes.getNotesBySubjectAmount().forEach((subject, amount) -> 
                System.out.printf("Тема: %s | Заметок: %d%n", subject, amount));
        System.out.println();
        
        /*
        webNotes.getNotes("immutable").forEach((subject, noteSet) -> 
                noteSet.stream().forEach(note ->
                        System.out.printf("Тема: %s | Заметка:%n%s%n%n", 
                                subject, note.note())));
        System.out.println();
        
        webNotes.getNotes(new LinkedHashSet<>(List.of("try", "finally", "break")))
                .forEach((subject, noteSet) -> 
                        noteSet.stream().forEach(note ->
                                System.out.printf("Тема: %s | Заметка:%n%s%n%n", 
                                        subject, note.note())));
        System.out.println();
    
        try {
            for (int i = 0; i < 23; i++) {
                Note note = webNotes.getRandomNote();
                System.out.printf("Тема: %s | Заметка:%n%s%n%n", note.subject(), note.note());
                //Thread.sleep(690);
            }          
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        */
        
        System.out.println();
        webNotes.getAllNotes().stream()
                .forEach(note -> System.out.printf("Тема: %s | Заметка:%n%s%n%n", 
                        note.subject(), note.note()));
    }
    
    
    private static void creating(WebNotes webNotes) {
        webNotes.createNote(
                new Note("тестовая1", "тестовая1"),
                new Note("тестовая2", "тестовая2"),
                new Note("тестовая3", "тестовая3")
        );
    }
    
    
    private static void updating(WebNotes webNotes) {      
        webNotes.getAllNotes().stream()
                .filter(note -> Set.of("тестовая1", "тестовая2", "тестовая3")
                                   .contains(note.note()))
                .forEach(note -> webNotes.updateNote(note.note() + "_UPDATED", note.id()));                     
                      
                /*                    
                .forEach(note -> System.out.printf(
                        "Before: %s%nAfter:%nЗаметка: %s%nId: %d%n",
                        note, note.note() + "_UPDATED", note.id())); */
                    
    }
    
    
    private static void deleting(WebNotes webNotes) {
        webNotes.getAllNotes().stream()
                .filter(note -> Set.of("тестовая1", "тестовая2", "тестовая3")
                                   .contains(note.subject()))
                .forEach(note -> webNotes.deleteNote(note.id()));
                //.forEach(System.out::println);
                      
                
    }
}