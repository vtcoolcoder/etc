import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class PostgresConnectionTester {
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DBNAME = "my_rough_notes";
    private static final String URL = BASE_URL + DBNAME;
    private static final String USER = "vtszet";
    private static final String PASSWORD = "&A7#h0[@W/*z?";
    
    private static final String SQL = "SELECT * FROM %s".formatted("temp");
    

    public static void main(String[] args) throws Exception {
        Class.forName(DRIVER_NAME);
        
        try (
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String subject = resultSet.getString("subject");
                String note = resultSet.getString("note");
                System.out.printf("ID: %d | Тема: %s | Заметка: %s%n", id, subject, note);
            }    
        }
    }
}


