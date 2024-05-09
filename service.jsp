<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<% 
    //SpringConfig.setBean(request);
    APIServlet API;
    
    AnnotationConfigApplicationContext context; 
                
    try {      
        context =
            new AnnotationConfigApplicationContext(SpringConfig.class); 
    } catch (Exception e) {
        context.registerBean(
                "httpServletRequest",
                HttpServletRequest.class,
                () -> request);
                
        API = context.getBean(APIServlet.class); 
    }
%>