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
    @Bean
    public Connection connection(DBConfig dbConfig) {
        Connection result = null;
        
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
        return () -> {
            try {
                statement.executeUpdate(queriesData.getTransactionRollBack());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    
    
    @Bean
    public Supplier<ResultSet> allNotesResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getAllNotes());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };     
    }
    
    
    @Bean
    public Supplier<ResultSet> allNotesWithoutIdResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getAllNotesWithoutId());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    
    
    @Bean
    public Supplier<ResultSet> distinctSubjectsResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getDistinctSubjects());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };     
    }
    
    
    @Bean
    public Supplier<ResultSet> allIdResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getAllId());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };      
    }
    
    
    @Bean
    public Supplier<ResultSet> notesBySubjectAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getNotesBySubjectAmount());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }; 
    }
    
    
    @Bean
    public Supplier<ResultSet> allSubjectsAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getAllSubjectsAmount());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };      
    }
    
    
    @Bean
    public Supplier<ResultSet> allNotesAmountResultSet(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                return statement.executeQuery(queriesData.getAllNotesAmount());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };     
    }
    
    
    @Bean
    public Runnable trimUpdate(Statement statement, QueriesData queriesData) {
        return () -> {
            try {
                statement.executeUpdate(queriesData.getTrimUpdate());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    
    
    @Bean
    public Supplier<Set<Note>> allNotes(
            @Qualifier("allNotesResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Supplier<Set<Note>> allNotesWithoutId(
            @Qualifier("allNotesWithoutIdResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Supplier<Set<String>> distinctSubjects(
            @Qualifier("distinctSubjectsResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Supplier<Set<Integer>> allId(
            @Qualifier("allIdResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Supplier<Map<String, Integer>> notesBySubjectAmount(
            @Qualifier("notesBySubjectAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Supplier<Integer> allSubjectsAmount(
            @Qualifier("allSubjectsAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Supplier<Integer> allNotesAmount(
            @Qualifier("allNotesAmountResultSet") Supplier<ResultSet> supplier) 
    {
        return () -> {
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
    }
    
    
    @Bean
    public Function<Integer, String> noteById(
            @Qualifier("noteByIdPreparedStatement") PreparedStatement statement) 
    {
        return id -> {
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
    }
    
    
    @Bean
    public Function<Integer, String> noteFragment(
            @Qualifier("noteFragmentPreparedStatement") PreparedStatement statement)
    {
        return id -> {
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
    }    
    
    
    @Bean
    public Function<String, Set<Note>> specificNote(
            @Qualifier("specificNotePreparedStatement") PreparedStatement statement)
    {
        return subject -> {
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
    }
    
    
    @Bean
    public Function<String, Set<Note>> fullSpecific(
            @Qualifier("fullSpecificPreparedStatement") PreparedStatement statement)
    {
        return subject -> {
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
    }
    
    
    @Bean
    public Supplier<Note> random(
            @Qualifier("randomPreparedStatement") PreparedStatement statement,
            @Qualifier("allId") Supplier<Set<Integer>> supplier)
    {
        return () -> {
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
    public BiConsumer<String, Integer> updateNote(
            @Qualifier("updateNotePreparedStatement") PreparedStatement statement)
    {
        return (trimmedNote, id) -> {
            try {
                statement.setString(1, trimmedNote);
                statement.setInt(2, id);
                statement.executeUpdate();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
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
    public Consumer<Integer> deleteNote(
            @Qualifier("deleteNotePreparedStatement") PreparedStatement statement)
    {
        return id -> {
            try {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
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
 }