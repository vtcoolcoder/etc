import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import jdbc_template.DBConfig;


@Configuration
@ComponentScan("jdbc_template")
@PropertySource("jdbc_template/config.properties")
@PropertySource("jdbc_template/queries.properties")
public class Config {
    
    @Bean
    public DataSource dataSource(DBConfig config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName(config.getDriverName());
        dataSource.setUrl(config.getURL());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());
        
        return dataSource;
    }
    
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}