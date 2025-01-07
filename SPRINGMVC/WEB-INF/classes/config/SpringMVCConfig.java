package classes.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;


@Configuration
@ComponentScan("controllers")
@EnableWebMvc
public class SpringMVCConfig implements WebMvcConfigurer {

    private static final String PREFIX = "/WEB-INF/views/";
    private static final String SUFFIX = ".html";
    

    private final ApplicationContext applicationContext;
    
    
    @Autowired
    public SpringMVCConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = 
                new SpringResourceTemplateResolver();
            
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix(PREFIX);
        templateResolver.setSuffix(SUFFIX);
        
        return templateResolver;
    }
    
    
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        
        return templateEngine;
    }
    
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}