<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="myservlets.APIServlet" %>
<%@ page import="using_spring.SpringConfig" %>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext" %>
<% 
    SpringConfig.setBean(request);
    
    AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(SpringConfig.class);
     
    APIServlet API = context.getBean(APIServlet.class); 
%>