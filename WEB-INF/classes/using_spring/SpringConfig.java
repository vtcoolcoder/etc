package using_spring;


import lombok.SneakyThrows;

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

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


@Configuration
@ComponentScan("using_spring")
//@ComponentScan("myservlets")
@PropertySource("using_spring/config.properties")
@PropertySource("using_spring/queries.properties")
public class SpringConfig {
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


    @Bean
    @SneakyThrows
    public Connection connection(DBConfig dbConfig) {
        //Connection result = null;
        
        //return tryCatchWrapping(() -> {
            Class.forName(dbConfig.getDriverName());
            return DriverManager.getConnection(
                    dbConfig.getURL(), 
                    dbConfig.getUser(), 
                    dbConfig.getPassword());
       // });
        
        /*
        try {
            Class.forName(dbConfig.getDriverName());
            result = DriverManager.getConnection(
                    dbConfig.getURL(), 
                    dbConfig.getUser(), 
                    dbConfig.getPassword());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        return result;
        */
    }
    
    
    @Bean
    @Primary
    //@Scope("prototype")
    @SneakyThrows
    public Statement statement(Connection connection) {
        return connection.createStatement();
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement noteByIdPreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getNoteById());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement noteFragmentPreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getNoteFragment());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement specificNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getSpecificNote());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement fullSpecificPreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getFullSpecific());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement randomPreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getRandom());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement addNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getAddNote());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement backupPreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getBackup());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement updateNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getUpdateNote());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement transactionUpdatePreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getTransactionUpdate());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement deleteNotePreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getDeleteNote());
    }
    
    
    @Bean
    @SneakyThrows
    public PreparedStatement transactionDeletePreparedStatement(Connection connection, QueriesData queriesData) {
        return connection.prepareStatement(queriesData.getTransactionDelete());
    }
    
    
    @Bean
    public Runnable transactionRollBack(Statement statement, QueriesData queriesData) {
        return () -> executeUpdate(statement::executeUpdate, queriesData.getTransactionRollBack()); 
        //statement.executeUpdate(queriesData.getTransactionRollBack());
        //return () -> tryCatchWrapping(() -> statement.executeUpdate(queriesData.getTransactionRollBack()));
        
        /*
        {
            try {
                statement.executeUpdate(queriesData.getTransactionRollBack());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> allNotesResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllNotes());
        
        //statement.executeQuery(queriesData.getAllNotes());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getAllNotes())); 
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getAllNotes());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };     
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> allNotesWithoutIdResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllNotesWithoutId());
        
        //statement.executeQuery(queriesData.getAllNotesWithoutId());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getAllNotesWithoutId()));
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getAllNotesWithoutId());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> distinctSubjectsResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getDistinctSubjects());
        
        //statement.executeQuery(queriesData.getDistinctSubjects());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getDistinctSubjects()));
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getDistinctSubjects());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };     
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> allIdResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllId());
        
        //statement.executeQuery(queriesData.getAllId());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getAllId())); 
        
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getAllId());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };      
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> notesBySubjectAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getNotesBySubjectAmount());
        
        //statement.executeQuery(queriesData.getNotesBySubjectAmount());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getNotesBySubjectAmount())); 
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getNotesBySubjectAmount());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }; 
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> allSubjectsAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllSubjectsAmount());
        
        //statement.executeQuery(queriesData.getAllSubjectsAmount());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getAllSubjectsAmount()));
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getAllSubjectsAmount());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };      
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<ResultSet> allNotesAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> executeQuery(statement::executeQuery, queriesData.getAllNotesAmount());
        
        //statement.executeQuery(queriesData.getAllNotesAmount());
        
        //return () -> tryCatchWrapping(() -> statement.executeQuery(queriesData.getAllNotesAmount()));
        
        /*
        {
            try {
                return statement.executeQuery(queriesData.getAllNotesAmount());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };     
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Runnable trimUpdate(Statement statement, QueriesData queriesData) {
        return () -> executeUpdate(statement::executeUpdate, queriesData.getTrimUpdate());
        
        //statement.executeUpdate(queriesData.getTrimUpdate());
        
        //return () -> tryCatchWrapping(() -> statement.executeUpdate(queriesData.getTrimUpdate()));
        
        /*
        {
            try {
                statement.executeUpdate(queriesData.getTrimUpdate());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Set<Note>> allNotes(
            @Qualifier("allNotesResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                Set<Note> result = new LinkedHashSet<>();  
            
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String subject = resultSet.getString("subject");
                    String note = resultSet.getString("note");
                    result.add(new Note(id, subject, note));
                }    
                
                return result;
        }); 
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                Set<Note> result = new LinkedHashSet<>();  
            
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String subject = resultSet.getString("subject");
                    String note = resultSet.getString("note");
                    result.add(new Note(id, subject, note));
                }    
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Set<Note>> allNotesWithoutId(
            @Qualifier("allNotesWithoutIdResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                Set<Note> result = new LinkedHashSet<>();    
            
                while (resultSet.next()) {
                    String subject = resultSet.getString("subject");
                    String note = resultSet.getString("note");
                    result.add(new Note(subject, note));
                }    
                
                return result;
        }); 
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                Set<Note> result = new LinkedHashSet<>();    
            
                while (resultSet.next()) {
                    String subject = resultSet.getString("subject");
                    String note = resultSet.getString("note");
                    result.add(new Note(subject, note));
                }    
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Set<String>> distinctSubjects(
            @Qualifier("distinctSubjectsResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                Set<String> result = new LinkedHashSet<>();
                   
                while (resultSet.next()) {     
                    String subject = resultSet.getString("subject"); 
                    result.add(subject);
                }    
                
                return result;
        }); 
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                Set<String> result = new LinkedHashSet<>();
                   
                while (resultSet.next()) {     
                    String subject = resultSet.getString("subject"); 
                    result.add(subject);
                }    
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };        
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Set<Integer>> allId(
            @Qualifier("allIdResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                Set<Integer> result = new LinkedHashSet<>();
            
                while (resultSet.next()) {     
                    int id = resultSet.getInt("id"); 
                    result.add(id);
                }    
                
                return result;
        }); 
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                Set<Integer> result = new LinkedHashSet<>();
            
                while (resultSet.next()) {     
                    int id = resultSet.getInt("id"); 
                    result.add(id);
                }    
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Map<String, Integer>> notesBySubjectAmount(
            @Qualifier("notesBySubjectAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                Map<String, Integer> result = new LinkedHashMap<>(); 
            
                while (resultSet.next()) {    
                    String subject = resultSet.getString("subject"); 
                    int amount = resultSet.getInt("amount"); 
                    result.put(subject, amount);
                }    
                
                return result; 
        });
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                Map<String, Integer> result = new LinkedHashMap<>(); 
            
                while (resultSet.next()) {    
                    String subject = resultSet.getString("subject"); 
                    int amount = resultSet.getInt("amount"); 
                    result.put(subject, amount);
                }    
                
                return result; 
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Integer> allSubjectsAmount(
            @Qualifier("allSubjectsAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                int result = -1;
                
                while (resultSet.next()) {              
                    result = resultSet.getInt("amount");    
                }  
                
                return result;
        });
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                int result = -1;
                
                while (resultSet.next()) {              
                    result = resultSet.getInt("amount");    
                }  
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Integer> allNotesAmount(
            @Qualifier("allNotesAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> preparedExecuteQuery(() -> {
                ResultSet resultSet = supplier.get();
                int result = -1;
                
                while (resultSet.next()) {              
                    result = resultSet.getInt("amount");    
                }  
                
                return result;
        });
        
        /*
        {
            try {
                ResultSet resultSet = supplier.get();
                int result = -1;
                
                while (resultSet.next()) {              
                    result = resultSet.getInt("amount");    
                }  
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Function<Integer, String> noteById(
            @Qualifier("noteByIdPreparedStatement") PreparedStatement statement) 
    {
        return id -> preparedExecuteQuery(() -> {
                String result = null;
                
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result = resultSet.getString("note");
                }
                
                return result;
        });
        
        /*
        {
            try {
                String result = null;
                
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result = resultSet.getString("note");
                }
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Function<Integer, String> noteFragment(
            @Qualifier("noteFragmentPreparedStatement") PreparedStatement statement)
    {
        return id -> preparedExecuteQuery(() -> {
                String result = null;
                
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result = resultSet.getString("fragment");
                }
                
                return result;
        });
        
        /*
        {
            try {
                String result = null;
                
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result = resultSet.getString("fragment");
                }
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }    
    
    
    @Bean
    @SneakyThrows
    public Function<String, Set<Note>> specificNote(
            @Qualifier("specificNotePreparedStatement") PreparedStatement statement)
    {
        return subject -> preparedExecuteQuery(() -> {
                Set<Note> result = new LinkedHashSet<>();
                
                statement.setString(1, subject);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result.add(new Note(
                            resultSet.getString("subject"), 
                            resultSet.getString("note")));   
                }
                
                return result;
        });
        
        /*
        {
            try {
                Set<Note> result = new LinkedHashSet<>();
                
                statement.setString(1, subject);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result.add(new Note(
                            resultSet.getString("subject"), 
                            resultSet.getString("note")));   
                }
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Function<String, Set<Note>> fullSpecific(
            @Qualifier("fullSpecificPreparedStatement") PreparedStatement statement)
    {
        return subject -> preparedExecuteQuery(() -> {
                Set<Note> result = new LinkedHashSet<>();
                
                statement.setString(1, subject);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result.add(new Note(
                            resultSet.getInt("id"),
                            resultSet.getString("subject"), 
                            resultSet.getString("note")));   
                }
                
                return result;
        });
        
        /*
         {
            try {
                Set<Note> result = new LinkedHashSet<>();
                
                statement.setString(1, subject);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result.add(new Note(
                            resultSet.getInt("id"),
                            resultSet.getString("subject"), 
                            resultSet.getString("note")));   
                }
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    @SneakyThrows
    public Supplier<Note> random(
            @Qualifier("randomPreparedStatement") PreparedStatement statement,
            @Qualifier("allId") Supplier<Set<Integer>> supplier)
    {
        return () -> preparedExecuteQuery(() -> {
                Note result = null;
                
                Set<Integer> allID = supplier.get();
                int randomId = (new java.util.LinkedList<Integer>(allID)).get(
                        new java.util.Random().nextInt(allID.size()));
            
                statement.setInt(1, randomId);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result = new Note(
                            resultSet.getString("subject"), 
                            resultSet.getString("note"));
                }
                
                return result;
        });
        
        
        
        /*
        {
            try {
                Note result = null;
                
                Set<Integer> allID = supplier.get();
                int randomId = (new java.util.LinkedList<Integer>(allID)).get(
                        new java.util.Random().nextInt(allID.size()));
            
                statement.setInt(1, randomId);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    result = new Note(
                            resultSet.getString("subject"), 
                            resultSet.getString("note"));
                }
                
                return result;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean    
    public BiConsumer<String, String> addNote(
            @Qualifier("addNotePreparedStatement") PreparedStatement statement)
    {
        return (subject, note) -> {
            try {
                statement.setString(1, subject);
                statement.setString(2, note);
                statement.executeUpdate();
            } catch (PSQLException ex) {
                System.err.printf(
                        "[ОШИБКА]: Такой контент уже существует!%n[CONTENT]:%n%s%n", note);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    
    
    @Bean
    public Consumer<Integer> backup(
            @Qualifier("backupPreparedStatement") PreparedStatement statement)
    {
        return id -> {
            while (true) {
                try {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    break;
                } catch (PSQLException ex) {
                    System.err.println(ex.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception inner) {
                        throw new RuntimeException(inner);
                    }
                    continue;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
    
    
    @Bean
    @SneakyThrows
    public BiConsumer<String, Integer> updateNote(
            @Qualifier("updateNotePreparedStatement") PreparedStatement statement)
    {
        return (trimmedNote, id) -> preparedExecuteUpdate((trimmedNoteLmb, idLmb) -> {
            statement.setString(1, trimmedNoteLmb);
            statement.setInt(2, idLmb);
            statement.executeUpdate();
        }, trimmedNote, id);
        
        /*
        {
            statement.setString(1, trimmedNote);
            statement.setInt(2, id);
            statement.executeUpdate();
        };
        */
        
        
        
        /*
        {
            try {
                statement.setString(1, trimmedNote);
                statement.setInt(2, id);
                statement.executeUpdate();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    public BiConsumer<String, Integer> transactionUpdate(
            @Qualifier("transactionUpdatePreparedStatement") PreparedStatement statement,
            @Qualifier("transactionRollBack") Runnable rollback)
    {
        return (trimmedNote, id) -> {
            while (true) {
                try {
                    statement.setInt(1, id);
                    statement.setString(2, trimmedNote);
                    statement.setInt(3, id);
                    statement.executeUpdate();
                    break;
                } catch (PSQLException ex) {
                    System.err.println(ex.getMessage());
                    rollback.run();
                    continue;
                    
                    /*
                    System.err.println(ex.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception inner) {
                        throw new RuntimeException(inner);
                    }
                    */
                    
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
    
    
    @Bean
    @SneakyThrows
    public Consumer<Integer> deleteNote(
            @Qualifier("deleteNotePreparedStatement") PreparedStatement statement)
    {
        return id -> preparedExecuteUpdate(idLmb -> {
            statement.setInt(1, idLmb);
            statement.executeUpdate();
        }, id);
        
        /*
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        };
        */
        
        
       
        /*
        {
            try {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        */
    }
    
    
    @Bean
    public Consumer<Integer> transactionDelete(
            @Qualifier("transactionDeletePreparedStatement") PreparedStatement statement,
            @Qualifier("transactionRollBack") Runnable rollback)
    {
        return id -> {
            while (true) {
                try {
                    statement.setInt(1, id);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                    break;
                } catch (PSQLException ex) {
                    System.err.println(ex.getMessage());
                    rollback.run();
                    continue;
                    
                    /*
                    System.err.println(ex.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception inner) {
                        throw new RuntimeException(inner);
                    }
                    */
                    
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
    
    
    /*
    private static void tryCatchWrapping(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static <T> T tryCatchWrapping(Supplier<T> action) {
        try {
            return action.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
    
    /*
    private static void tryCatchWrapping(Runnable action, Runnable anotherAction) {
        try {
            action.run();
        } catch(PSQLException e) {
            anotherAction.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
    
    /*
    private static void something(Consumer<Statement> action, String query) {
        try {
            action.accept(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
    
    
    private static ResultSet executeQuery(SQLFunction<String, ResultSet> action, String query) {
        try {
            return action.apply(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static void executeUpdate(SQLFunction<String, Integer> action, String query) {
        try {
            action.apply(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static void preparedExecuteUpdate(SQLConsumer<Integer> action, int id) {
        try {
            action.accept(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static void preparedExecuteUpdate(
            SQLBiConsumer<String, Integer> action, 
            String trimmedNote, 
            int id)
    {
        try {
            action.accept(trimmedNote, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static <R> R preparedExecuteQuery(SQLSupplier<R> action) {
        return useTemplate(action);
        
        /*
        try {
            return action.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */
    }
    
    
    private static <T, R> R useTemplate(T action) {
        try {
            if (action instanceof Runnable runnable) {
                runnable.run();
            } else if (action instanceof SQLSupplier<R> supplier) {
                return supplier.get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 }