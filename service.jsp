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
    
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext();
            
    context.registerBean(
            "request",
            HttpServletRequest.class,
            () -> request);
            
    context.register(SpringConfig.class);
            
    //AnnotationConfigApplicationContext context =
    //        new AnnotationConfigApplicationContext();
                              
    APIServlet API = context.getBean(APIServlet.class); 
%>