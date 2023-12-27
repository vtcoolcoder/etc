package myjdbc;

import com.mysql.cj.jdbc.Driver; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {
    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String SUFFIX = "?characterEncoding=utf8"; 
    private static final String USER = "noter"; 
    private static final String PWD = "&Q9<д±c⅛Л4⸮йN7§f2Ъ"; 
    private static final String DEFAULT_DATABASE = "my_rough_notes";
    
    private final String URL;
    
    public Connector() throws SQLException { this(DEFAULT_DATABASE); }
    
    public Connector(final String databaseName) throws SQLException { 
        URL = BASE_URL + databaseName + SUFFIX; 
        registerJDBCDriver();
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    } 
    
    private static void registerJDBCDriver() throws SQLException { 
        DriverManager.registerDriver(new Driver()); 
    }
}