package using_spring;


//import lombok.Cleanup;
//import lombok.SneakyThrows;
import lombok.Getter;
import lombok.ToString;

//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Autowired;

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.DriverManager;
//import java.sql.Statement;
//import java.sql.ResultSet;

//@ToString
@Component
@Getter
public final class DBConfig {

    private final String driverName; 
    private final String URL;
    private final String user; 
    private final String password;  
   
    /*
    @Value("${query.selectAll}")
    private String testSQL; 
    
    @Value("${table.id}")
    private String id; 
    
    @Value("${table.subject}")
    private String subject; 
    
    @Value("${table.note}")
    private String note; 
    
    @Value("${show.format}")
    private String showFormat; 
    */
    
    public DBConfig(
            @Value("${config.driverName}") String driverName,
            @Value("${config.URL}") String URL,
            @Value("${config.user}") String user,
            @Value("${config.password}") String password) 
    {
        this.driverName = driverName;
        this.URL = URL;
        this.user = user;
        this.password = password;
    }
}


