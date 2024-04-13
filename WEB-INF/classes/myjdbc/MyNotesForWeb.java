package myjdbc;


import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

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
    
    private record NotesBySubject(String subject, Set<Note> notes) {}

    private interface Queries {
    
        String SUBJECT = "subject";
        String JAVANOTES = "java";
        String NOTE = "note";
        String ID = "id";
        
        String NOTE_BY_ID = "SELECT %s FROM %s WHERE %s = ?"
                .formatted(NOTE, JAVANOTES, ID);
        
        String DISTINCT_SUBJECTS = 
                "SELECT DISTINCT %s FROM %s".formatted(SUBJECT, JAVANOTES);
        
        String SPECIFIC_NOTE =
                """
                SELECT %s, %s
                FROM %s
                WHERE %s = ?
                """.formatted(SUBJECT, NOTE, JAVANOTES, SUBJECT);
                
        String FULL_SPECIFIC =
                """
                SELECT %s, %s, %s
                FROM %s
                WHERE %s = ?
                """.formatted(ID, SUBJECT, NOTE, JAVANOTES, SUBJECT);
        
        String ALL_NOTES = "SELECT %s, %s FROM %s".formatted(SUBJECT, NOTE, JAVANOTES);
        
        String ADD_NOTE = "INSERT INTO %s (%s, %s) VALUES (?, ?)"
                .formatted(JAVANOTES, SUBJECT, NOTE);
                
        String UPDATE_NOTE = "UPDATE %s SET %s = ? WHERE %s = ?"
                .formatted(JAVANOTES, NOTE, ID);
                
        String DELETE_NOTE = "DELETE FROM %s WHERE %s = ?"
                .formatted(JAVANOTES, ID);
    }
    
       
    private static final Set<String> SUBJECTS = new TreeSet<>();
    private static final Set<Note> NOTES = new TreeSet<>();
    private static final Set<Note> SELECTED_NOTES = new TreeSet<>();
    
       
    private static PreparedStatement globalPreparedStatement;
    
    
    private static void templatedPrepare(Handler<PreparedStatement> consumer, String SQL) {
        engine(connection -> prepareStatementEngine(connection, statement -> {
                if (statement instanceof PreparedStatement prepare) {  
                    consumer.accept(prepare);
                } 
        }, SQL));
    }
    
    
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
    
    
    public static Map<String, Set<Note>> getNotesBySelectedSubjects(final String selected) {
        Set<String> oneSubject = new HashSet<>();
        oneSubject.add(selected);
        return getNotesBySelectedSubjects(oneSubject);
    }
    
    
    public static Map<String, Set<Note>> getNotesBySelectedSubjects(final Set<String> selected) {       
        Map<String, Set<Note>> result = new TreeMap<>(String::compareTo);
        
        templatedPrepare(prepare -> {
                globalPreparedStatement = prepare;
                    
                selected.stream()
                        .filter(SUBJECTS::contains)
                        .map(MyNotesForWeb::processSpecificNotes)
                        .forEach(notes -> result.put(
                                notes.subject(), notes.notes()));
        }, Queries.FULL_SPECIFIC);
                               
        return result;
    }
    
    
    public static void main(String[] args) {    
        for (Note note : getAllNotes()) {
            System.out.println(note);
        }
    }  
    
    
    private static void statementEngine(Connection connection,
                                        Handler<Statement> consumer) {
        try (Statement statement = connection.createStatement()) 
        {
            consumer.accept(statement);
        } catch (SQLException inner) {
            throw new RuntimeException(inner);
        }
    }
    
    
    private static void prepareStatementEngine(Connection connection, 
                                               Handler<Statement> consumer,
                                               String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            consumer.accept(statement);
        } catch (SQLException inner) {
            throw new RuntimeException(inner);
        }
    }
    
    
    private static void engine(ConnectionHandler handler) {
        try {
            new Processor(new Connector(), handler);
        } catch (Exception outer) {
            throw new RuntimeException(outer);
        }
    }
    
    
    private static void update(Handler<Statement> consumer) {
        engine(connection -> statementEngine(connection, consumer));
    }
    
       
    private static void processSubjects(final Statement statement) throws SQLException {     
        ResultSet subjects = statement.executeQuery(Queries.DISTINCT_SUBJECTS);   
        
        iterateByResultSet(subjects, subjs -> {
                String currentSubject = subjs.getString(Queries.SUBJECT);
                if (! (currentSubject.isEmpty() || currentSubject.isBlank())) {
                    SUBJECTS.add(currentSubject);
                }
        });   
    }
    
      
    private static void processAllNotes(final Statement statement) throws SQLException {
        ResultSet notes = statement.executeQuery(Queries.ALL_NOTES);   
        
        iterateByResultSet(notes, nts -> {
                String noteSubject = nts.getString(Queries.SUBJECT);
                String noteContent = nts.getString(Queries.NOTE);
                NOTES.add(new Note(noteSubject, noteContent));
        });  
    }
    
    
    private static NotesBySubject processSpecificNotes(final String requiredSubject) {
        NotesBySubject result = null;
        Set<Note> resultingNotes = new TreeSet<>();
        
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
    
    
    private static void iterateByResultSet(ResultSet resultSet, 
                                           Handler<ResultSet> consumer) {
        try {
            while (resultSet.next()) {
                consumer.accept(resultSet);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }     
    }
}