package jdbc_template;


import java.lang.reflect.Method;
import java.util.Arrays;

import lombok.Getter;
import lombok.Cleanup;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public final class Queries {

    private final String noteById;
    private final String noteFragment;
    private final String distinctSubjects;
    private final String specificNote;
    private final String fullSpecific;
    private final String allNotes;
    private final String allNotesWithoutId;
    private final String addNote;
    private final String updateNote;
    private final String updateNoteByContent;
    private final String deleteNote;
    private final String deleteNoteByContent;
    private final String notesBySubjectAmount;
    private final String notesBySubjects;
    private final String notesByContent;
    private final String allSubjectsAmount;
    private final String allNotesAmount;
    private final String allId;
    private final String random;
    private final String backup;
    private final String backupByContent;
    private final String trimUpdate;
    private final String transactionUpdate;
    private final String transactionUpdateByContent;
    private final String transactionDelete;
    private final String transactionDeleteByContent;
    private final String transactionRollBack;
    

    public Queries(
            @Value("${query.noteById}") String noteById,
            @Value("${query.noteFragment}") String noteFragment,
            @Value("${query.distinctSubjects}") String distinctSubjects,
            @Value("${query.specificNote}") String specificNote,
            @Value("${query.fullSpecific}") String fullSpecific,
            @Value("${query.allNotes}") String allNotes,
            @Value("${query.allNotesWithoutId}") String allNotesWithoutId,
            @Value("${query.addNote}") String addNote,
            @Value("${query.updateNote}") String updateNote,
            @Value("${query.updateNoteByContent}") String updateNoteByContent,
            @Value("${query.deleteNote}") String deleteNote,
            @Value("${query.deleteNoteByContent}") String deleteNoteByContent,
            @Value("${query.notesBySubjectAmount}") String notesBySubjectAmount,
            @Value("${query.notesBySubjects}") String notesBySubjects,
            @Value("${query.notesByContent}") String notesByContent,
            @Value("${query.allSubjectsAmount}") String allSubjectsAmount,
            @Value("${query.allNotesAmount}") String allNotesAmount,
            @Value("${query.allId}") String allId,
            @Value("${query.random}") String random,
            @Value("${query.backup}") String backup,
            @Value("${query.backupByLikeContent}") String backupByContent,
            @Value("${query.trimUpdate}") String trimUpdate,
            @Value("${query.transactionUpdate}") String transactionUpdate,
            @Value("${query.transactionUpdateByContent}") String transactionUpdateByContent,
            @Value("${query.transactionDelete}") String transactionDelete,
            @Value("${query.transactionDeleteByContent}") String transactionDeleteByContent,
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
        this.updateNoteByContent = updateNoteByContent;
        this.deleteNote = deleteNote;
        this.deleteNoteByContent = deleteNoteByContent;
        this.notesBySubjectAmount = notesBySubjectAmount;
        this.notesBySubjects = notesBySubjects;
        this.notesByContent = notesByContent;
        this.allSubjectsAmount = allSubjectsAmount;
        this.allNotesAmount = allNotesAmount;
        this.allId = allId;
        this.random = random;
        this.backup = backup;
        this.backupByContent = backupByContent;
        this.trimUpdate = trimUpdate;
        this.transactionUpdate = transactionUpdate;
        this.transactionUpdateByContent = transactionUpdateByContent;
        this.transactionDelete = transactionDelete;
        this.transactionDeleteByContent = transactionDeleteByContent;
        this.transactionRollBack = transactionRollBack;
    }
    
      
    public static void main(String[] args) {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(Config.class);
        var data = context.getBean("queriesData", Queries.class);
        
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