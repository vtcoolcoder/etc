<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 
    //SpringConfig.setBean(request);
    //APIServlet API;
    
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class, request.getClass()); 
            
    context.registerBean(
                "httpServletRequest",
                HttpServletRequest.class,
                () -> request);
                
    //context.register(SpringConfig.class);
                
    //AnnotationConfigApplicationContext primaryContext = 
    //        new AnnotationConfigApplicationContext(SpringConfig.class); 
                              
    APIServlet API = context.getBean(APIServlet.class); 
%>