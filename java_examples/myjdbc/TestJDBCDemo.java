import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.sql.ResultSet; 

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;


public class TestJDBCDemo {      
    private static final Set<String> SUBJECT_LIST = new HashSet<>();
    private static final Set<String> CUSTOM_SUBJECT_LIST = new HashSet<>();
    
    private static final String OLD_DATABASE_NAME = "java";
    
    private static String[] commandLineArgs;
    
    
    public static void main(String[] args) throws Exception {
        commandLineArgs = args;
        new Processor(new Connector(OLD_DATABASE_NAME), TestJDBCDemo::processConnection);
    }
    
     
    private static void processConnection(final Connection connection) {
        try (Statement statement = connection.createStatement()) {          
            processItems(statement);       
        } catch (SQLException ex) {
            System.err.println(ErrorMessages.INVALID_QUERY.getMessage());
        }
    }
    
    
    private static void processItems(final Statement statement) throws SQLException {
        processSubjects(statement);
        processNotes(statement);     
    }
    
    
    private static void processSubjects(final Statement statement) throws SQLException {
        ResultSet subjects = statement.executeQuery(Queries.UNIQUE_SUBJECTS.getQuery());    
        fillSubjectList(subjects);
        tryFillCustomSubjectList();
    }
    
    
    private static void fillSubjectList(final ResultSet subjects) throws SQLException {           
        iterateByResultSet(subjects, result -> {
            String subject = result.getString(Fields.subject.name()); 
            SUBJECT_LIST.add(subject);
        });
    }
    
    
    private static void tryFillCustomSubjectList() {
        Arrays.stream(commandLineArgs)
              .filter(SUBJECT_LIST::contains)
              .forEach(CUSTOM_SUBJECT_LIST::add);
    }
    
    
    private static void processNotes(final Statement statement) throws SQLException {    
        defineSubjectList().stream()
                           .forEach(item -> {
                               try {
                                   String gettingNotes = 
                                       String.format(Queries.NOTE_FORMAT.getQuery(), item); 
                                   ResultSet notes = statement.executeQuery(gettingNotes);
                                   printNotes(item, notes);
                               } catch (SQLException ex) {
                                   throw new RuntimeException(ex);
                               }
                           });
    }
    
    
    private static Set<String> defineSubjectList() {
        return commandLineArgs.length == 0 ? SUBJECT_LIST : CUSTOM_SUBJECT_LIST;
    }
    
    
    private static void printNotes(final String subject, 
                                   final ResultSet notes) 
                                   throws SQLException {     
        iterateByResultSet(notes, result -> {
            String note = result.getString(Fields.note.name());
            System.out.printf(Queries.GENERAL_FORMAT.getQuery(), subject, note);
        });
    }
    
    
    private static void iterateByResultSet(final ResultSet item, 
                                           final IterationHandler<ResultSet> func)
                                           throws SQLException {
        while (item.next()) { func.handle(item); }
    }
}