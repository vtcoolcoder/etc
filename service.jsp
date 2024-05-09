<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="org.springframework.context.annotation.Bean" %>
<%@ page import="org.springframework.context.annotation.Configuration" %>
<%@ page import="org.springframework.context.annotation.ComponentScan" %>
<%@ page import="org.springframework.context.annotation.PropertySource" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 
    //SpringConfig.setBean(request);
    /*
    @ComponentScan(basePackages = { "using_spring", "myservlets" })
    @PropertySource("using_spring/config.properties")
    @PropertySource("using_spring/queries.properties")
    @Configuration
    */
    /*
    @Configuration
    class Config extends SpringConfig {
        @Bean
        public HttpServletRequest httpServletRequest() {
            return request;
        }
    }
    */
    
    Class<?> myConfig = new SpringConfig() {
        @Bean
        public HttpServletRequest httpServletRequest() {
            return request;
        }
    }.getClass();
    
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(myConfig);
                              
    APIServlet API = context.getBean(APIServlet.class); 
%>