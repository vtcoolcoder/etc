<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 
    //SpringConfig.setBean(request);
    //APIServlet API;
    
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(); 
            
    context.registerBean(
                "httpServletRequest",
                HttpServletRequest.class,
                () -> request);
                
    AnnotationConfigApplicationContext primaryContext = 
            new AnnotationConfigApplicationContext(SpringConfig.class); 
                              
    API = primaryContext.getBean(APIServlet.class); 
%>