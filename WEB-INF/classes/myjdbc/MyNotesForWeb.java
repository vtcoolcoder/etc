package myjdbc;


import static java.util.stream.Collectors.toMap;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;


public class MyNotesForWeb {  
    @FunctionalInterface
    private interface Handler<T> {
        void accept(T o) throws SQLException;
    }
    
    @FunctionalInterface
    private interface BiHandler<T, E> {
        void accept(T o, E e) throws SQLException;
    }
    
     
    private record NotesBySubject(String subject, Set<Note> notes) {}


    private interface Queries {
    
        String SUBJECT = "subject";
        String JAVANOTES = "java";
        String NOTE = "note";
        String ID = "id";
        
        String NOTE_BY_ID = 
        """
        SELECT %s 
        FROM %s 
        WHERE %s = ?
        """.formatted(NOTE, JAVANOTES, ID);
        
        String DISTINCT_SUBJECTS = 
        """
        SELECT DISTINCT %s 
        FROM %s 
        ORDER BY LOWER(%s)
        """.formatted(SUBJECT, JAVANOTES, SUBJECT);
        
        String SPECIFIC_NOTE =
        """
        SELECT %s, %s
        FROM %s
        WHERE %s = ?
        ORDER BY %s
        """.formatted(SUBJECT, NOTE, JAVANOTES, SUBJECT, NOTE);
                
        String FULL_SPECIFIC =
        """
        SELECT %s, %s, %s
        FROM %s
        WHERE %s = ?
        ORDER BY %s
        """.formatted(ID, SUBJECT, NOTE, JAVANOTES, SUBJECT, NOTE);
        
        String ALL_NOTES = 
        """
        SELECT %s, %s 
        FROM %s 
        ORDER BY LOWER(%s), %s
        """.formatted(SUBJECT, NOTE, JAVANOTES, SUBJECT, NOTE);
        
        String ADD_NOTE = 
        """
        INSERT INTO %s (%s, %s) 
        VALUES (TRIM(?), TRIM(?))
        """.formatted(JAVANOTES, SUBJECT, NOTE);
                
        String UPDATE_NOTE = 
        """
        UPDATE %s 
        SET %s = TRIM(?) 
        WHERE %s = ?
        """.formatted(JAVANOTES, NOTE, ID);
                
        String DELETE_NOTE = 
        """
        DELETE FROM %s 
        WHERE %s = ?
        """.formatted(JAVANOTES, ID);
    }
    
       
    private static Set<String> SUBJECTS = new LinkedHashSet<>();
    private static Set<Note> NOTES = new LinkedHashSet<>();
        
    private static PreparedStatement globalPreparedStatement;
    
       
    public static void deleteNote(final int id) { 
        templatedPrepare(prepare -> {
                prepare.setInt(1, id);
                prepare.executeUpdate();
        }, Queries.DELETE_NOTE);
    }
    
       
    public static void updateNote(final String content, final int id) {
        templatedPrepare(prepare -> {
                prepare.setString(1, content);
                prepare.setInt(2, id);
                prepare.executeUpdate();
        }, Queries.UPDATE_NOTE);
    }
    
    
    public static String getNoteContentById(final int id) {
        StringBuilder noteContent = new StringBuilder();
     
        templatedPrepare(prepare -> {
                prepare.setInt(1, id);
                ResultSet resultSet = prepare.executeQuery();
                iterateByResultSet(resultSet, rsltSet -> {
                        noteContent.append(rsltSet.getString(Queries.NOTE));
                });
        }, Queries.NOTE_BY_ID);
    
        return noteContent.toString();    
    }
    
    
    public static void addNote(final Note... notes) {   
        templatedPrepare(prepare -> {
                for (Note note : notes) {
                    prepare.setString(1, note.subject());
                    prepare.setString(2, note.note());
                    prepare.executeUpdate();
                }
        }, Queries.ADD_NOTE);
    }
    
    
    public static Set<String> getSubjectSet() { 
        update(MyNotesForWeb::processSubjects); 
        return SUBJECTS; 
    }
    
    
    public static Set<Note> getAllNotes() { 
        update(MyNotesForWeb::processAllNotes);
        return NOTES; 
    }
    
    
    public static 
    Map<String, Set<Note>> getNotesBySelectedSubjects(final String selected) {
        return getDataUsingProcessing(result -> {
                if (SUBJECTS.contains(selected)) {
                    NotesBySubject notes = processSpecificNotes(selected);
                    result.put(notes.subject(), notes.notes());
                }
        });
    }
    
    
    public static 
    Map<String, Set<Note>> getNotesBySelectedSubjects(final Set<String> selected) {  
        return getDataUsingProcessing(result -> {
                result.putAll(selected.stream()
                        .map(MyNotesForWeb::processSpecificNotes)   
                        .collect(toMap(NotesBySubject::subject, NotesBySubject::notes, 
                                (k, v) -> v, LinkedHashMap::new)));
        });
    }
    
    
    public static void main(String[] args) {  
        if (isExistCLArgs(args)) {
            if (isPrintAvailableSubjects(args)) {
                printSubjects(getSubjectSet());
            } else {
                printExistingCustomSubjectsNotes(
                        getExistingCustomSubjects(args));
            }     
        } else {
            printNotes(getAllNotes());
        }         
    }
    
    
    private static boolean isExistCLArgs(final String[] args) { return args.length > 0; }
    
    
    private static boolean isPrintAvailableSubjects(final String[] args) {
        return "-l".equals(args[0]) || "--list".equals(args[0]);
    }
    
    
    private static void printSubjects(final Set<String> subjects) {
        subjects.stream().forEach(System.out::println);
    }
    
    
    private static 
    Set<String> getExistingCustomSubjects(final String... customSubjects) {
        Set<String> result;
        (result = new LinkedHashSet<>(Arrays.asList(customSubjects)))
                .retainAll(new LinkedHashSet<>(getSubjectSet()));
        return result;
    } 
    
    
    private static 
    void printExistingCustomSubjectsNotes(final Set<String> existingCustomSubjects) {
        getNotesBySelectedSubjects(existingCustomSubjects)
                .forEach((key, value) -> printNotes(value));
    }
    
    
    private static void printNotes(final Set<Note> notes) {
        notes.stream().forEach(System.out::println);
    }
    
    
    private static Map<String, Set<Note>> getDataUsingProcessing(
            final Handler<Map<String, Set<Note>>> action) 
    {
        Map<String, Set<Note>> result = new LinkedHashMap<>();
        
        templatedPrepare((prepare, resultLmb) -> {
                globalPreparedStatement = prepare;
                action.accept(resultLmb);    
        }, result, Queries.FULL_SPECIFIC);
                               
        return result;
    }
    
    
    private static void templatedPrepare(
            final Handler<PreparedStatement> consumer, 
            final String SQL) 
    {
        useBaseTemplate(prepare -> consumer.accept(prepare), SQL);
    }
        
    
    private static void templatedPrepare(
            final BiHandler<PreparedStatement, Map<String, Set<Note>>> consumer, 
            final Map<String, Set<Note>> result,
            final String SQL) 
    { 
        useBaseTemplate(prepare -> consumer.accept(prepare, result), SQL); 
    }
        
      
    private static void useBaseTemplate(
            final Handler<PreparedStatement> action, 
            final String SQL) 
    {
        engine(connection -> prepareStatementEngine(connection, statement -> {
                if (statement instanceof PreparedStatement prepare) {  
                    action.accept(prepare);
                } 
        }, SQL));
    }
    
    
    private static void prepareStatementEngine(
            final Connection connection, 
            final Handler<Statement> consumer,
            final String sql) 
    {
        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            consumer.accept(statement);
        } catch (SQLException inner) {
            throw new RuntimeException(inner);
        }
    }
    
    
    private static void statementEngine(
            final Connection connection,
            final Handler<Statement> consumer) 
    {                                
        try (Statement statement = connection.createStatement()) 
        {
            consumer.accept(statement);
        } catch (SQLException inner) {
            throw new RuntimeException(inner);
        }
    }
    
    
    private static void engine(final ConnectionHandler handler) {
        try {
            new Processor(new Connector(), handler);
        } catch (Exception outer) {
            throw new RuntimeException(outer);
        }
    }
    
    
    private static void update(final Handler<Statement> consumer) {
        engine(connection -> statementEngine(connection, consumer));
    }
    
       
    private static 
    void processSubjects(final Statement statement) throws SQLException {
        SUBJECTS = new LinkedHashSet<>();
             
        ResultSet subjects = statement.executeQuery(Queries.DISTINCT_SUBJECTS);   
        
        iterateByResultSet(subjects, subjs -> {
                String currentSubject = subjs.getString(Queries.SUBJECT);
                if (isCurrentSubjectValid(currentSubject)) {
                    SUBJECTS.add(currentSubject);
                }
        });   
    }
    
    
    private static boolean isCurrentSubjectValid(final String currentSubject) {
        return ! (currentSubject.isEmpty() || currentSubject.isBlank());
    }
    
      
    private static 
    void processAllNotes(final Statement statement) throws SQLException {
        NOTES = new LinkedHashSet<>();
        
        ResultSet notes = statement.executeQuery(Queries.ALL_NOTES);   
        
        iterateByResultSet(notes, nts -> {
                String noteSubject = nts.getString(Queries.SUBJECT);
                String noteContent = nts.getString(Queries.NOTE);
                NOTES.add(new Note(noteSubject, noteContent));
        });  
    }
    
    
    private static 
    NotesBySubject processSpecificNotes(final String requiredSubject) {
        NotesBySubject result = null;
        Set<Note> resultingNotes = new LinkedHashSet<>();
        
        try {
            globalPreparedStatement.setString(1, requiredSubject);   
            ResultSet notes = globalPreparedStatement.executeQuery();  
            
            iterateByResultSet(notes, nts -> {
                    int id = nts.getInt(Queries.ID);
                    String noteSubject = nts.getString(Queries.SUBJECT);
                    String noteContent = nts.getString(Queries.NOTE);
                    resultingNotes.add(new Note(id, noteSubject, noteContent));
            });
            
            result = new NotesBySubject(requiredSubject, resultingNotes);
                                                 
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }                     
        
        return result;     
    }
    
    
    private static void iterateByResultSet(
            final ResultSet resultSet, 
            final Handler<ResultSet> consumer) 
    {
        try {
            while (resultSet.next()) {
                consumer.accept(resultSet);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }     
    }
}