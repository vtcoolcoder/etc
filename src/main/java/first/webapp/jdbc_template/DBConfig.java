package jdbc_template;


import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public final class DBConfig {

    private final String driverName; 
    private final String URL;
    private final String user; 
    private final String password;  
   
    
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


