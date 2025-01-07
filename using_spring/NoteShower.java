package using_spring;


import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.Getter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;
import java.util.Map;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


@Component
@Getter
public final class NoteShower {
 
    private final Supplier<Set<Note>> allNotes;  
    private final Supplier<Set<Note>> allNotesWithoutId; 
    private final Supplier<Set<String>> distinctSubjects;  
    private final Supplier<Set<Integer>> allId;  
    private final Supplier<Map<String, Integer>> notesBySubjectAmount; 
    private final Supplier<Integer> allSubjectsAmount; 
    private final Supplier<Integer> allNotesAmount;
    private final Runnable trimUpdate;  
    private final Function<Integer, String> noteById;    
    private final Function<String, Set<Note>> specificNote;  
    private final Function<String, Set<Note>> fullSpecific;  
    private final Supplier<Note> random;   
    private final BiConsumer<String, String> addNote;   
    private final Consumer<Integer> backup;  
    private final BiConsumer<String, Integer> updateNote;   
    private final BiConsumer<String, Integer> transactionUpdate;
    private final Consumer<Integer> deleteNote;   
    private final Consumer<Integer> transactionDelete;
    
    
    @Autowired
    public NoteShower(
            @Qualifier("allNotes") Supplier<Set<Note>> allNotes,
            @Qualifier("allNotesWithoutId") Supplier<Set<Note>> allNotesWithoutId,
            @Qualifier("distinctSubjects") Supplier<Set<String>> distinctSubjects,
            @Qualifier("allId") Supplier<Set<Integer>> allId,
            @Qualifier("notesBySubjectAmount") Supplier<Map<String, Integer>> notesBySubjectAmount,
            @Qualifier("allSubjectsAmount") Supplier<Integer> allSubjectsAmount,
            @Qualifier("allNotesAmount") Supplier<Integer> allNotesAmount,
            @Qualifier("trimUpdate") Runnable trimUpdate,
            @Qualifier("noteById") Function<Integer, String> noteById,
            @Qualifier("specificNote") Function<String, Set<Note>> specificNote,
            @Qualifier("fullSpecific") Function<String, Set<Note>> fullSpecific,
            @Qualifier("random") Supplier<Note> random,
            @Qualifier("addNote") BiConsumer<String, String> addNote,
            @Qualifier("backup") Consumer<Integer> backup,
            @Qualifier("updateNote") BiConsumer<String, Integer> updateNote,
            @Qualifier("transactionUpdate") BiConsumer<String, Integer> transactionUpdate,
            @Qualifier("deleteNote") Consumer<Integer> deleteNote,
            @Qualifier("transactionDelete") Consumer<Integer> transactionDelete) 
    {
        this.allNotes = allNotes;
        this.allNotesWithoutId = allNotesWithoutId;
        this.distinctSubjects = distinctSubjects;
        this.allId = allId;
        this.notesBySubjectAmount = notesBySubjectAmount;
        this.allSubjectsAmount = allSubjectsAmount;
        this.allNotesAmount = allNotesAmount;
        this.trimUpdate = trimUpdate;
        this.noteById = noteById;
        this.specificNote = specificNote;
        this.fullSpecific = fullSpecific;
        this.random = random;
        this.addNote = addNote;
        this.backup = backup;
        this.updateNote = updateNote;
        this.transactionUpdate = transactionUpdate;
        this.deleteNote = deleteNote;
        this.transactionDelete = transactionDelete;
    }
     
    
    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        NoteShower noteShower = context.getBean(NoteShower.class);
        
        System.out.println("Список тем:");
        noteShower.distinctSubjects.get().stream().forEach(System.out::println); 
        
        System.err.println();
        
        System.out.println("Все ID:");
        noteShower.allId.get().stream().forEach(System.out::println);
        
        System.err.println();
        
        System.out.println("Количество заметок по каждой теме:");
        noteShower.notesBySubjectAmount.get().forEach(
                (subject, amount) -> System.out.printf(
                        "Тема: %s | Количество заметок: %d%n", subject, amount));
            
        System.err.println();
        
        System.out.printf("Всего тем: %d%n", noteShower.allSubjectsAmount.get());
        System.out.printf("Всего заметок: %d%n", noteShower.allNotesAmount.get());
        
        System.err.println();
        
        System.out.println("Все заметки:");
        noteShower.allNotes.get().stream().forEach(System.out::println);     
    }
    
    //queriesData.getNoteById()
    //queriesData.getSpecificNote()
    //queriesData.getFullSpecific() 
    //queriesData.getRandom()
    //queriesData.getDistinctSubjects()    +
    //queriesData.getAllNotes()    +
    //queriesData.getNotesBySubjectAmount()    +
    //queriesData.getAllSubjectsAmount()    +
    //queriesData.getAllNotesAmount()    +
    //queriesData.getAllId()    +
    //queriesData.getAddNote()
    //queriesData.getUpdateNote()
    //queriesData.getDeleteNote()
    //queriesData.getBackup()
}