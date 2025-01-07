import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import classes.config.SpringMVCConfig;


public class MyFirstWebAppInitializer 
        extends AbstractAnnotationConfigDispatcherServletInitializer {
           
    @Override 
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }
    
    
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringMVCConfig.class };
    }
    
    
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
