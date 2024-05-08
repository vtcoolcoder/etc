package using_spring;


import lombok.SneakyThrows;
import lombok.Setter;
import lombok.Getter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Primary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.postgresql.util.PSQLException;


import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import java.util.Random;


@Configuration
@ComponentScan("using_spring")
//@ComponentScan("myservlets")
@PropertySource("using_spring/config.properties")
@PropertySource("using_spring/queries.properties")
public class SpringConfig {

    private static final Random RANDOM = new Random();
    
    
    @FunctionalInterface
    private interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }
    
    @FunctionalInterface
    private interface SQLSupplier<R> {
        R get() throws SQLException;
    }
    
    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
    
    @FunctionalInterface
    private interface SQLBiConsumer<T, E> {
        void accept(T t, E e) throws SQLException;
    }
    
    @FunctionalInterface
    private interface SQLRunnable {
        void run() throws SQLException;
    }
    
    
    @Setter
    @Getter
    private static class Flag {
        private boolean repeatLoop;
    }


    @Bean
    @SneakyThrows
    public Connection connection(DBConfig dbConfig) {
        Class.forName(dbConfig.getDriverName());
        return DriverManager.getConnection(
                dbConfig.getURL(), 
                dbConfig.getUser(), 
                dbConfig.getPassword());
    }
    
    
    @Bean
    @Primary
    @SneakyThrows
    public Statement statement(Connection connection) {
        return connection.createStatement();
    }
    
    
    @Bean
    public PreparedStatement noteByIdPreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getNoteById());
    }
    
    
    @Bean
    public PreparedStatement noteFragmentPreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getNoteFragment());
    }
    
    
    @Bean
    public PreparedStatement specificNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getSpecificNote());
    }
    
    
    @Bean
    public PreparedStatement fullSpecificPreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getFullSpecific());
    }
    
    
    @Bean
    public PreparedStatement randomPreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getRandom());
    }
    
    
    @Bean
    public PreparedStatement addNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getAddNote());
    }
    
    
    @Bean
    public PreparedStatement backupPreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getBackup());
    }
    
    
    @Bean
    public PreparedStatement updateNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getUpdateNote());
    }
    
    
    @Bean
    public PreparedStatement transactionUpdatePreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getTransactionUpdate());
    }
    
    
    @Bean
    public PreparedStatement deleteNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getDeleteNote());
    }
    
    
    @Bean
    public PreparedStatement transactionDeletePreparedStatement(Connection connection, QueriesData queriesData) {
        return getPreparedStatement(connection, queriesData.getTransactionDelete());
    }
    
    
    @Bean
    public Runnable transactionRollBack(Statement statement, QueriesData queriesData) {
        return () -> executeUpdate(statement::executeUpdate, queriesData.getTransactionRollBack()); 
    }
    
    
    @Bean
    public Supplier<ResultSet> allNotesResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllNotes());
    }
    
    
    @Bean
    public Supplier<ResultSet> allNotesWithoutIdResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllNotesWithoutId());
    }
    
    
    @Bean
    public Supplier<ResultSet> distinctSubjectsResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getDistinctSubjects());
    }
    
    
    @Bean
    public Supplier<ResultSet> allIdResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllId());
    }
    
    
    @Bean
    public Supplier<ResultSet> notesBySubjectAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getNotesBySubjectAmount());
    }
    
    
    @Bean
    public Supplier<ResultSet> allSubjectsAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllSubjectsAmount());
    }
    
    
    @Bean
    public Supplier<ResultSet> allNotesAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllNotesAmount());
    }
    
    
    @Bean
    public Runnable trimUpdate(Statement statement, QueriesData queriesData) {
        return () -> executeUpdate(statement::executeUpdate, queriesData.getTrimUpdate());
    }
    
    
    @Bean
    public Supplier<Set<Note>> allNotes(
            @Qualifier("allNotesResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                //ResultSet resultSet = supplier.get();
                final Set<Note> RESULT = new LinkedHashSet<>();  
                
                iterateByResultSet(supplier, resultSetLmb -> 
                        RESULT.add(new Note(resultSetLmb.getInt("id"), 
                                resultSetLmb.getString("subject"), 
                                resultSetLmb.getString("note"))));
                
                /*
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String subject = resultSet.getString("subject");
                    String note = resultSet.getString("note");
                    result.add(new Note(id, subject, note));
                }    
                */
                
                return RESULT;
        }); 
    }
    
    
    @Bean
    public Supplier<Set<Note>> allNotesWithoutId(
            @Qualifier("allNotesWithoutIdResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                //ResultSet resultSet = supplier.get();
                final Set<Note> RESULT = new LinkedHashSet<>();   
                
                iterateByResultSet(supplier, resultSetLmb -> 
                        RESULT.add(new Note(resultSetLmb.getString("subject"), 
                                resultSetLmb.getString("note")))); 
                
                /*
                while (resultSet.next()) {
                    String subject = resultSet.getString("subject");
                    String note = resultSet.getString("note");
                    result.add(new Note(subject, note));
                }    
                */
                
                return RESULT;
        }); 
    }
    
    
    @Bean
    public Supplier<Set<String>> distinctSubjects(
            @Qualifier("distinctSubjectsResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                //ResultSet resultSet = supplier.get();
                final Set<String> RESULT = new LinkedHashSet<>();
                
                iterateByResultSet(supplier, resultSetLmb -> 
                        RESULT.add(resultSetLmb.getString("subject")));
                   
                /*   
                while (resultSet.next()) {     
                    String subject = resultSet.getString("subject"); 
                    result.add(subject);
                }    
                */
                
                return RESULT;
        }); 
    }
    
    
    @Bean
    public Supplier<Set<Integer>> allId(
            @Qualifier("allIdResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                //ResultSet resultSet = supplier.get();
                final Set<Integer> RESULT = new LinkedHashSet<>();
                
                iterateByResultSet(supplier, resultSetLmb -> 
                        RESULT.add(resultSetLmb.getInt("id")));
                
                /*
                while (resultSet.next()) {     
                    int id = resultSet.getInt("id"); 
                    result.add(id);
                }    
                */
                
                return RESULT;
        }); 
    }
    
    
    @Bean
    public Supplier<Map<String, Integer>> notesBySubjectAmount(
            @Qualifier("notesBySubjectAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                //ResultSet resultSet = supplier.get();
                final Map<String, Integer> RESULT = new LinkedHashMap<>(); 
                
                iterateByResultSet(supplier, resultSetLmb -> 
                        RESULT.put(resultSetLmb.getString("subject"), 
                                resultSetLmb.getInt("amount")));
            
                /*
                while (resultSet.next()) {    
                    String subject = resultSet.getString("subject"); 
                    int amount = resultSet.getInt("amount"); 
                    result.put(subject, amount);
                }    
                */
                
                return RESULT; 
        });
    }
    
    
    @Bean
    public Supplier<Integer> allSubjectsAmount(
            @Qualifier("allSubjectsAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> getContent(supplier));
        
        /*
         {
                ResultSet resultSet = supplier.get();
                int result = -1;
                
                while (resultSet.next()) {              
                    result = resultSet.getInt("amount");    
                }  
                
                return result;
        });
        */
    }
    
    
    @Bean
    public Supplier<Integer> allNotesAmount(
            @Qualifier("allNotesAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> getContent(supplier));
        
        /*
        {
                //ResultSet resultSet = supplier.get();
                //int result = -1;
                
                @Getter
                @Setter
                class Helper {
                    private int result = -1;
                }
                
                final Helper HELPER = new Helper();
                
                iterateByResultSet(supplier.get(), resultSetLmb -> 
                        HELPER.setResult(resultSetLmb.getInt("amount")));
                
                
                while (resultSet.next()) {              
                    result = resultSet.getInt("amount");    
                }  
                
                
                return HELPER.getResult();
        });
        */
    }
    
    
    @Bean
    public Function<Integer, String> noteById(
            @Qualifier("noteByIdPreparedStatement") PreparedStatement statement) 
    {
        return id -> preparedExecuteQuery(() -> getContent(statement, id, "note"));
        
        /*
        {
                String result = null;
                
                statement.setInt(1, id);
                //ResultSet resultSet = statement.executeQuery();
                
                iterateByResultSet(statement, resultSetLmb -> {
                
                });
                
                while (resultSet.next()) {
                    result = resultSet.getString("note");
                }
                
                return result;
        });
        */
    }
    
    
    @Bean
    public Function<Integer, String> noteFragment(
            @Qualifier("noteFragmentPreparedStatement") PreparedStatement statement)
    {
        return id -> preparedExecuteQuery(() -> getContent(statement, id, "fragment"));
        
        /*
        {
                final StringBuilder RESULT = new StringBuilder();
                
                statement.setInt(1, id); */
                //ResultSet resultSet = statement.executeQuery();
                
                /*
                iterateByResultSet(statement, resultSetLmb -> 
                    RESULT.append(resultSetLmb.getString("fragment")));*/
                
                /*
                while (resultSet.next()) {
                    result = resultSet.getString("fragment");
                }
                */
                /*
                return RESULT.toString();
        });*/
    }    
    
    
    @Bean
    public Function<String, Set<Note>> specificNote(
            @Qualifier("specificNotePreparedStatement") PreparedStatement statement)
    {
        return subject -> preparedExecuteQuery(() -> getContent(statement, subject,
                resultSetLmb -> new Note(resultSetLmb.getString("subject"), 
                        resultSetLmb.getString("note"))));      
        
        /*
        {
                final Set<Note> RESULT = new LinkedHashSet<>();
                
                statement.setString(1, subject);
                //ResultSet resultSet = statement.executeQuery();
                
                iterateByResultSet(statement, resultSetLmb -> 
                    RESULT.add(new Note(
                            resultSetLmb.getString("subject"), 
                            resultSetLmb.getString("note"))));
                
                /*
                while (resultSet.next()) {
                    result.add(new Note(
                            resultSet.getString("subject"), 
                            resultSet.getString("note")));   
                }
                
                
                return RESULT;
        });
        */
    }
    
    
    @Bean
    public Function<String, Set<Note>> fullSpecific(
            @Qualifier("fullSpecificPreparedStatement") PreparedStatement statement)
    {
        return subject -> preparedExecuteQuery(() -> getContent(statement, subject,
                (resultSetLmb) -> new Note(resultSetLmb.getInt("id"),
                            resultSetLmb.getString("subject"), 
                            resultSetLmb.getString("note"))));
        
        /*
        {
                final Set<Note> RESULT = new LinkedHashSet<>();
                
                statement.setString(1, subject);
                //ResultSet resultSet = statement.executeQuery();
                
                iterateByResultSet(statement, resultSetLmb -> 
                    RESULT.add(new Note(
                            resultSetLmb.getInt("id"),
                            resultSetLmb.getString("subject"), 
                            resultSetLmb.getString("note"))));
                
                /*
                while (resultSet.next()) {
                    result.add(new Note(
                            resultSet.getInt("id"),
                            resultSet.getString("subject"), 
                            resultSet.getString("note")));   
                }
                
                
                return RESULT;
        });
        */
    }
    
    
    @Bean
    public Supplier<Note> random(
            @Qualifier("randomPreparedStatement") PreparedStatement statement,
            @Qualifier("allId") Supplier<Set<Integer>> supplier)
    {
        return () -> preparedExecuteQuery(() -> {
                @Getter
                @Setter
                class Helper {
                    private Note result;
                }
                
                final Helper HELPER = new Helper();
                
                Set<Integer> allID = supplier.get();
                int randomId = (new LinkedList<Integer>(allID)).get(
                        RANDOM.nextInt(allID.size()));
            
                statement.setInt(1, randomId);
                
                iterateByResultSet(statement, resultSetLmb -> 
                        HELPER.setResult(new Note(
                                resultSetLmb.getString("subject"), 
                                resultSetLmb.getString("note"))));
                
                return HELPER.getResult();
        });
    }
    
    
    @Bean    
    public BiConsumer<String, String> addNote(
            @Qualifier("addNotePreparedStatement") PreparedStatement statement)
    {
        return (subject, note) -> tryCatchWrapping(
                () -> {
                    statement.setString(1, subject);
                    statement.setString(2, note);
                    statement.executeUpdate();
                },
                (exception) -> {
                    System.err.println(exception.getMessage());
                    System.err.printf(
                        "[ОШИБКА]: Такой контент уже существует!%n[CONTENT]:%n%s%n", 
                        note);
                });
    }
    
    
    @Bean
    public Consumer<Integer> backup(
            @Qualifier("backupPreparedStatement") PreparedStatement statement)
    {
        return id -> retryingExecuteUpdate(
                () -> {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                },
                (exception) -> {
                    System.err.println(exception.getMessage());
                });
    }
    
    
    @Bean
    public BiConsumer<String, Integer> updateNote(
            @Qualifier("updateNotePreparedStatement") PreparedStatement statement)
    {
        return (trimmedNote, id) -> preparedExecuteUpdate((trimmedNoteLmb, idLmb) -> {
            statement.setString(1, trimmedNoteLmb);
            statement.setInt(2, idLmb);
            statement.executeUpdate();
        }, trimmedNote, id);
    }
    
    
    @Bean
    public BiConsumer<String, Integer> transactionUpdate(
            @Qualifier("transactionUpdatePreparedStatement") PreparedStatement statement,
            @Qualifier("transactionRollBack") Runnable rollback)
    {
        return (trimmedNote, id) -> retryingExecuteUpdate(
                () -> {
                    statement.setInt(1, id);
                    statement.setString(2, trimmedNote);
                    statement.setInt(3, id);
                    statement.executeUpdate();
                },
                (exception) -> {
                    System.err.println(exception.getMessage());
                    rollback.run();
                });
    }
    
    
    @Bean
    public Consumer<Integer> deleteNote(
            @Qualifier("deleteNotePreparedStatement") PreparedStatement statement)
    {
        return id -> preparedExecuteUpdate(idLmb -> {
            statement.setInt(1, idLmb);
            statement.executeUpdate();
        }, id);
    }
    
    
    @Bean
    public Consumer<Integer> transactionDelete(
            @Qualifier("transactionDeletePreparedStatement") PreparedStatement statement,
            @Qualifier("transactionRollBack") Runnable rollback)
    {
        return id -> retryingExecuteUpdate(
                () -> {
                    statement.setInt(1, id);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                }, 
                (exception) -> {
                    System.err.println(exception.getMessage());
                    rollback.run();
                });
    }
    
    
    private static void retryingExecuteUpdate(SQLRunnable ok, Consumer<Exception> fail) {
        final Flag flag = new Flag();
        
        do {
            tryCatchWrapping(ok, fail, flag);
        } while (flag.isRepeatLoop());
    }
    
    
    private static void tryCatchWrapping(SQLRunnable ok, Consumer<Exception> fail) {
        tryCatchWrapping(ok, fail, new Flag());
    }
    
    
    private static void tryCatchWrapping(SQLRunnable ok, Consumer<Exception> fail, Flag flag) {
        try {
            ok.run();
            flag.setRepeatLoop(false);
        } catch (PSQLException e) {
            fail.accept(e);
            flag.setRepeatLoop(true);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
       
    @SneakyThrows
    private static ResultSet executeQuery(SQLFunction<String, ResultSet> action, String query) {
        return action.apply(query);
    }
    
    
    @SneakyThrows
    private static void executeUpdate(SQLFunction<String, Integer> action, String query) {
        action.apply(query);
    }
    
    
    @SneakyThrows
    private static void preparedExecuteUpdate(SQLConsumer<Integer> action, int id) {
        action.accept(id);
    }
    
    
    @SneakyThrows
    private static void preparedExecuteUpdate(SQLBiConsumer<String, Integer> action, String trimmedNote, int id) {
        action.accept(trimmedNote, id);
    }
    
    
    @SneakyThrows
    private static <R> R preparedExecuteQuery(SQLSupplier<R> action) {
        return action.get(); 
    }
    
    
    @SneakyThrows
    private static PreparedStatement getPreparedStatement(Connection connection, String query) {
        return connection.prepareStatement(query);
    }
    
    
    @SneakyThrows
    private static void iterateByResultSet(PreparedStatement statement, SQLConsumer<ResultSet> action) {  
        ResultSet resultSet = statement.executeQuery();
        iterateByResultSet(resultSet, action);     
    }
    
    
    @SneakyThrows
    private static void iterateByResultSet(Supplier<ResultSet> supplier, SQLConsumer<ResultSet> action) {
        ResultSet resultSet = supplier.get();
        iterateByResultSet(resultSet, action);   
    }
    
    
    @SneakyThrows
    private static void iterateByResultSet(ResultSet resultSet, SQLConsumer<ResultSet> action) {
        while (resultSet.next()) {
            action.accept(resultSet);
        }
    }
    
    
    private static String getContent(PreparedStatement statement, int id, String request) {
        final StringBuilder RESULT = new StringBuilder();
        statement.setInt(1, id);
        iterateByResultSet(statement, 
                resultSetLmb -> RESULT.append(resultSetLmb.getString(request)));        
        return RESULT.toString();              
    }
    
    
    private static Set<Note> getContent(PreparedStatement statement, String subject, SQLFunction<ResultSet, Note> item) {
        final Set<Note> RESULT = new LinkedHashSet<>();
        statement.setString(1, subject);        
        iterateByResultSet(statement, resultSetLmb -> RESULT.add(item.apply(resultSetLmb)));                      
        return RESULT;              
    }
    
    
    private static int getContent(Supplier<ResultSet> supplier) {
        @Getter
        @Setter
        class Helper {
            private int result = -1;
        }
        
        final Helper HELPER = new Helper();
        
        iterateByResultSet(supplier.get(), resultSetLmb -> 
                HELPER.setResult(resultSetLmb.getInt("amount")));
        
        return HELPER.getResult();
    }
}