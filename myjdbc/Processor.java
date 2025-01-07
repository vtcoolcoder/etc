package myjdbc;

import java.sql.Connection;
import java.sql.SQLException;


public class Processor {
    public Processor(Connector connector, ConnectionHandler func) {
        try (Connection connection = connector.getConnection()) {     
            if (! connection.isClosed()) { 
                func.handleConnection(connection); 
            }          
        } catch (SQLException ex) {
            System.err.println(ErrorMessages.NO_CONNECTION);
        }
    }
}