package myjdbc;


import static java.util.stream.Collectors.toMap;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Arrays;

import java.util.Random;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;


public class WebNotes implements WebNotesAPI {  
    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T o) throws SQLException;
    }
    
    @FunctionalInterface
    private interface SQLBiConsumer<T, E> {
        void accept(T o, E e) throws SQLException;
    }
       
    private record NotesBySubject(String subject, Set<Note> notes) {}


    private static final String FULL_PARAM_NAME = "--list";
    private static final String BRIEF_PARAM_NAME = "-l";
    
    private static final int FIRST_ARG = 0;
    private static final int MIN_LENGTH = 0;
      
      
    private static Set<String> availableSubjects = new LinkedHashSet<>();
    private static Set<Note> availableNotes = new LinkedHashSet<>();
    private static Map<String, Integer> notesBySubjectAmount = new HashMap<>();
    private static List<Integer> allID = new ArrayList<>();
    
    private static int allSubjectsAmount;
    private static int allNotesAmount;
        
    private static PreparedStatement cachedPreparedStatement;
    
    
    @Override   
    public void deleteNote(final int id) { 
        backupNote(id);
        
        templatedPrepare(prepare -> {
                prepare.setInt(1, id);
                prepare.executeUpdate();
        }, Queries.DELETE_NOTE);
    }
    
    
    @Override   
    public void updateNote(final String content, final int id) {
        backupNote(id);
        
        templatedPrepare(prepare -> {
                prepare.setString(1, content);
                prepare.setInt(2, id);
                prepare.executeUpdate();
        }, Queries.UPDATE_NOTE);
    }
    
    
    @Override
    public String getNoteContent(final int id) {
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
    
    
    @Override
    public void createNote(final Note... notes) {   
        templatedPrepare(prepare -> {
                for (Note note : notes) {
                    prepare.setString(1, note.subject());
                    prepare.setString(2, note.note());
                    prepare.executeUpdate();
                }
        }, Queries.ADD_NOTE);
    }
    
    
    @Override
    public Set<String> getAllSubjects() { 
        update(WebNotes::processSubjects); 
        return availableSubjects; 
    }
    
    
    @Override
    public Set<Note> getAllNotes() { 
        update(WebNotes::processAllNotes);
        return availableNotes; 
    }
    
    
    @Override 
    public Map<String, Set<Note>> getNotes(final String selected) {
        return getDataUsingProcessing(result -> {
                if (availableSubjects.contains(selected)) {
                    NotesBySubject notes = processSpecificNotes(selected);
                    result.put(notes.subject(), notes.notes());
                }
        });
    }
    
    
    @Override
    public Map<String, Set<Note>> getNotes(final Set<String> selected) {  
        return getDataUsingProcessing(result -> {
                result.putAll(selected
                        .stream()
                        .map(WebNotes::processSpecificNotes)   
                        .collect(toMap(NotesBySubject::subject, NotesBySubject::notes, 
                                (k, v) -> v, LinkedHashMap::new)));
        });
    }
    
    
    @Override
    public Map<String, Integer> getNotesBySubjectAmount() {
        update(WebNotes::processNotesBySubjectAmount);
        return notesBySubjectAmount;
    }
    
    
    @Override
    public int getAllSubjectsAmount() {
        update(WebNotes::processAllSubjectsAmount);
        return allSubjectsAmount;
    }
    
    
    @Override
    public int getAllNotesAmount() {
        update(WebNotes::processAllNotesAmount);
        return allNotesAmount;
    }
    
    
    @Override
    public Note getRandomNote() {   
        class Helper { String subject, note; }
        final Helper helper = new Helper();
          
        List<Integer> allID = getAllID();
        int size = allID.size();
        final int randomId = allID.get(new Random().nextInt(size));
        
        templatedPrepare(prepare -> {
                prepare.setInt(1, randomId);
                ResultSet resultSet = prepare.executeQuery();
                iterateByResultSet(resultSet, rsltSet -> {
                        helper.subject = rsltSet.getString(Queries.SUBJECT);
                        helper.note = rsltSet.getString(Queries.NOTE);
                });
        }, Queries.RANDOM);
        
        return new Note(
                helper.subject != null ? helper.subject : "", 
                helper.note != null ? helper.note : "");
    }
    
    
    public static void main(String[] args) { 
        WebNotes webNotes = new WebNotes();
              
        if (isExistCLArgs(args)) {
            if (isPrintAvailableSubjects(args)) {
                printSubjects(webNotes.getAllSubjects());
            } else {
                printExistingCustomSubjectsNotes(
                        webNotes,
                        getExistingCustomSubjects(webNotes, args));
            }     
        } else {
            printNotes(webNotes.getAllNotes());
        }         
    }
    
    
    private static boolean isExistCLArgs(final String[] args) { return args.length > MIN_LENGTH; }
    
    
    private static boolean isPrintAvailableSubjects(final String[] args) {
        final String TRIMMED_FIRST_ARG = args[FIRST_ARG].trim();
        return BRIEF_PARAM_NAME.equals(TRIMMED_FIRST_ARG) 
                || FULL_PARAM_NAME.equals(TRIMMED_FIRST_ARG);
    }
    
    
    private static void printSubjects(final Set<String> subjects) {
        subjects.stream().forEach(System.out::println);
    }
    
       
    private static Set<String> getExistingCustomSubjects(
            final WebNotes webNotes, 
            final String... customSubjects) 
    {
        Set<String> result;
        (result = new LinkedHashSet<>(Arrays.asList(customSubjects)))
                .retainAll(new LinkedHashSet<>(webNotes.getAllSubjects()));
        return result;
    } 
    
       
    private static void printExistingCustomSubjectsNotes(
            final WebNotes webNotes, 
            final Set<String> existingCustomSubjects) 
    {
        webNotes.getNotes(existingCustomSubjects)
                .forEach((key, value) -> printNotes(value));
    }
    
    
    private static void printNotes(final Set<Note> notes) {
        notes.stream().forEach(System.out::println);
    }
    
    
    private static Map<String, Set<Note>> getDataUsingProcessing(
            final SQLConsumer<Map<String, Set<Note>>> action) 
    {
        Map<String, Set<Note>> result = new LinkedHashMap<>();
        
        templatedPrepare((prepare, resultLmb) -> {
                cachedPreparedStatement = prepare;
                action.accept(resultLmb);    
        }, result, Queries.FULL_SPECIFIC);
                               
        return result;
    }
    
    
    private static void templatedPrepare(
            final SQLConsumer<PreparedStatement> consumer, 
            final String SQL) 
    {
        useBaseTemplate(prepare -> consumer.accept(prepare), SQL);
    }
        
    
    private static void templatedPrepare(
            final SQLBiConsumer<PreparedStatement, Map<String, Set<Note>>> consumer, 
            final Map<String, Set<Note>> result,
            final String SQL) 
    { 
        useBaseTemplate(prepare -> consumer.accept(prepare, result), SQL); 
    }
        
      
    private static void useBaseTemplate(
            final SQLConsumer<PreparedStatement> action, 
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
            final SQLConsumer<Statement> consumer,
            final String SQL) 
    {
        try (PreparedStatement statement = connection.prepareStatement(SQL)) 
        {
            consumer.accept(statement);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    private static void statementEngine(
            final Connection connection,
            final SQLConsumer<Statement> consumer) 
    {                                
        try (Statement statement = connection.createStatement()) 
        {
            consumer.accept(statement);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    private static void engine(final ConnectionHandler handler) {
        try {
            new Processor(new Connector(), handler);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    private static void update(final SQLConsumer<Statement> consumer) {
        engine(connection -> statementEngine(connection, consumer));
    }
    
       
    private static 
    void processSubjects(final Statement statement) throws SQLException {
        availableSubjects = new LinkedHashSet<>();
             
        ResultSet subjects = statement.executeQuery(Queries.DISTINCT_SUBJECTS);   
        
        iterateByResultSet(subjects, subjs -> {
                String currentSubject = subjs.getString(Queries.SUBJECT);
                if (isCurrentSubjectValid(currentSubject)) {
                    availableSubjects.add(currentSubject);
                }
        });   
    }
    
    
    private static boolean isCurrentSubjectValid(final String currentSubject) {
        return ! (currentSubject.isEmpty() || currentSubject.isBlank());
    }
    
      
    private static 
    void processAllNotes(final Statement statement) throws SQLException {
        availableNotes = new LinkedHashSet<>();
        
        ResultSet notes = statement.executeQuery(Queries.ALL_NOTES);   
        
        iterateByResultSet(notes, nts -> {
                String noteSubject = nts.getString(Queries.SUBJECT);
                String noteContent = nts.getString(Queries.NOTE);
                availableNotes.add(new Note(noteSubject, noteContent));
        });  
    }
    
    
    private static 
    NotesBySubject processSpecificNotes(final String requiredSubject) {
        NotesBySubject result = null;
        Set<Note> resultingNotes = new LinkedHashSet<>();
        
        try {
            cachedPreparedStatement.setString(1, requiredSubject);   
            ResultSet notes = cachedPreparedStatement.executeQuery();  
            
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
            final SQLConsumer<ResultSet> consumer) 
    {
        try {
            while (resultSet.next()) {
                consumer.accept(resultSet);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }     
    }
    
    
    private static 
    void processNotesBySubjectAmount(final Statement statement) throws SQLException {
        ResultSet amount = statement.executeQuery(Queries.NOTES_BY_SUBJECT_AMOUNT);   
        
        iterateByResultSet(amount, amnt -> {
                String currentSubject = amnt.getString(Queries.SUBJECT);
                int currentAmount = amnt.getInt(Queries.AMOUNT);
                notesBySubjectAmount.put(currentSubject, currentAmount);
        });   
    }
    
    
    private static
    void processAllSubjectsAmount(final Statement statement) throws SQLException {
        ResultSet amount = statement.executeQuery(Queries.ALL_SUBJECTS_AMOUNT);   
        
        iterateByResultSet(amount, amnt -> {
                allSubjectsAmount = amnt.getInt(Queries.AMOUNT);         
        });   
    }
    
    
    private static
    void processAllNotesAmount(final Statement statement) throws SQLException {
        ResultSet amount = statement.executeQuery(Queries.ALL_NOTES_AMOUNT);   
        
        iterateByResultSet(amount, amnt -> {
                allNotesAmount = amnt.getInt(Queries.AMOUNT);         
        });   
    }
    
    
    private static
    void processAllID(final Statement statement) throws SQLException {
        ResultSet IDS = statement.executeQuery(Queries.ALL_ID);   
        
        iterateByResultSet(IDS, id -> {
                allID.add(id.getInt(Queries.ID));         
        });   
    }
    
    
    private static List<Integer> getAllID() {
        update(WebNotes::processAllID);
        return allID;
    }
    
    
    private static void backupNote(final int id) {
        templatedPrepare(prepare -> {
                prepare.setInt(1, id);  
                prepare.executeUpdate();
        }, Queries.BACKUP);
    }
}