package using_spring;


import java.lang.reflect.Method;
import java.util.Arrays;

import lombok.Getter;
import lombok.Cleanup;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public final class QueriesData {

    private final String noteById;
    private final String noteFragment;
    private final String distinctSubjects;
    private final String specificNote;
    private final String fullSpecific;
    private final String allNotes;
    private final String allNotesWithoutId;
    private final String addNote;
    private final String updateNote;
    private final String deleteNote;
    private final String notesBySubjectAmount;
    private final String allSubjectsAmount;
    private final String allNotesAmount;
    private final String allId;
    private final String random;
    private final String backup;
    private final String trimUpdate;
    private final String transactionUpdate;
    private final String transactionDelete;
    private final String transactionRollBack;
    

    public QueriesData(
            @Value("${query.noteById}") String noteById,
            @Value("${query.noteFragment}") String noteFragment,
            @Value("${query.distinctSubjects}") String distinctSubjects,
            @Value("${query.specificNote}") String specificNote,
            @Value("${query.fullSpecific}") String fullSpecific,
            @Value("${query.allNotes}") String allNotes,
            @Value("${query.allNotesWithoutId}") String allNotesWithoutId,
            @Value("${query.addNote}") String addNote,
            @Value("${query.updateNote}") String updateNote,
            @Value("${query.deleteNote}") String deleteNote,
            @Value("${query.notesBySubjectAmount}") String notesBySubjectAmount,
            @Value("${query.allSubjectsAmount}") String allSubjectsAmount,
            @Value("${query.allNotesAmount}") String allNotesAmount,
            @Value("${query.allId}") String allId,
            @Value("${query.random}") String random,
            @Value("${query.backup}") String backup,
            @Value("${query.trimUpdate}") String trimUpdate,
            @Value("${query.transactionUpdate}") String transactionUpdate,
            @Value("${query.transactionDelete}") String transactionDelete,
            @Value("${query.transactionRollBack}") String transactionRollBack) 
    {
        this.noteById = noteById;
        this.noteFragment = noteFragment;
        this.distinctSubjects = distinctSubjects;
        this.specificNote = specificNote;
        this.fullSpecific = fullSpecific;
        this.allNotes = allNotes;
        this.allNotesWithoutId = allNotesWithoutId;
        this.addNote = addNote;
        this.updateNote = updateNote;
        this.deleteNote = deleteNote;
        this.notesBySubjectAmount = notesBySubjectAmount;
        this.allSubjectsAmount = allSubjectsAmount;
        this.allNotesAmount = allNotesAmount;
        this.allId = allId;
        this.random = random;
        this.backup = backup;
        this.trimUpdate = trimUpdate;
        this.transactionUpdate = transactionUpdate;
        this.transactionDelete = transactionDelete;
        this.transactionRollBack = transactionRollBack;
    }
    
      
    public static void main(String[] args) {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        var data = context.getBean("queriesData", QueriesData.class);
        
        Arrays.stream(data.getClass().getMethods())
                .filter(method -> method.getName().startsWith("get") 
                        && !method.getName().endsWith("Class"))
                .forEach(method -> {
                    try {
                        System.out.printf("%s() : %s%n%n", 
                                method.getName(), 
                                method.invoke(data));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });              
    }
}